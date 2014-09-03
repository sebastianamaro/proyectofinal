<?php

namespace tuttifruttiweb\CategoryBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use FOS\RestBundle\Controller\Annotations as Rest;
use Doctrine\Common\Collections\ArrayCollection;
use Lsw\ApiCallerBundle\Call\HttpGetJson;
use Lsw\ApiCallerBundle\Call\HttpPostJson;
use Lsw\ApiCallerBundle\Caller\LoggingApiCaller;
use Symfony\Component\HttpFoundation\Response;

use tuttifruttiweb\UtilsBundle\Utils\ArraySorter as ArraySorter;
use tuttifruttiweb\UtilsBundle\Utils\Encoder as Encoder;
use tuttifruttiweb\CategoryBundle\Entity\Category;
use tuttifruttiweb\CategoryBundle\Entity\Word;
use tuttifruttiweb\CategoryBundle\Form\CategoryType;
use tuttifruttiweb\CategoryBundle\Form\CategoryFilterType;

class CategoryController extends Controller
{

    public function indexAction(Request $request)
    {
      $initialUrl = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'?';
        
      $form = $this->get('form.factory')->create(new CategoryFilterType(),
          null, array(
          'method' => 'get'
      ));
      $form->handleRequest($request);
      $url = $initialUrl;
      if ($this->get('request')->query->has($form->getName())) {
        foreach ($request->query->get('item_filter') as $key=>$filter) {
          if ($url !== $initialUrl) $url .=  '&';
          $url .= $key.'='.$filter;
        };
      }
      $categoriesData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      if ($categoriesData == null){
        $categoriesData = array();
      }
      $categories = array();
      foreach ($categoriesData as $key => $categoryData) {
          $category = new Category();
          $category->set($categoryData);
          $categories[] = $category;
      }
      
      $sort = $this->get('request')->query->get('sort','name');
      $direction = $this->get('request')->query->get('direction', 'ASC');
      
      $categories =  ArraySorter::sort($categories,$sort,$direction);
      $totalCategories = count($categories);

      $paginator = $this->get('ideup.simple_paginator');
      $categoriesPage = $paginator
            ->setItemsPerPage(10)
            ->paginate($categories)
            ->getResult();

      return $this->render('CategoryBundle:Category:index.html.twig', array(
          'categories' => $categoriesPage,
          'totalCategories'=>$totalCategories,
          'form' => $form->createView()
      ));
    }
    public function createAction(Request $request)
    {
        $category = new Category();
        $acceptedWord = new Word();

        $category->addAcceptedWord($acceptedWord);
        $form = $this->createCreateForm($category);
        $form->handleRequest($request);

        if ($form->isValid()) {
            $encoder = new Encoder();
            $objectCategory = $encoder->getSerializedObject($category);

            $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category');            
            $reqJson  = new HttpPostJson($url,$objectCategory);
            $categoryData = $this->get('api_caller')->call($reqJson);
            return $this->redirect($this->generateUrl('category_show', array('id' => $categoryData->id)));
        }

        return $this->render('CategoryBundle:Category:new.html.twig', array(
            'category' => $category,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Creates a form to create a Category entity.
     *
     * @param Category $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createCreateForm(Category $entity)
    {
        $form = $this->createForm(new CategoryType(), $entity, array(
            'action' => $this->generateUrl('category_create'),
            'method' => 'POST',
        ));

        return $form;
    }

    public function showAction($id)
    {
        $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
        $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
        $category = new Category();
        $category->set($categoryData);
        
        $paginator = $this->get('ideup.simple_paginator');
        $reportedWordsPage = $paginator
              ->setItemsPerPage(3,'reportedWords')
              ->paginate($category->getReportedWords(), 'reportedWords')
              ->getResult();
        $acceptedWordsPage = $paginator
              ->setItemsPerPage(3,'acceptedWords')
              ->paginate($category->getAcceptedWords(), 'acceptedWords')
              ->getResult();

        if (!$category) {
            throw $this->createNotFoundException('Unable to find Category entity.');
        }

        $deleteForm = $this->createDeleteForm($id);

        return $this->render('CategoryBundle:Category:show.html.twig', array(
            'category'      => $category,
            'delete_form' => $deleteForm->createView(),
            'reportedWordsPage' =>$reportedWordsPage,
            'acceptedWordsPage' =>$acceptedWordsPage,
        ));
    }

    /**
     * Displays a form to edit an existing Category entity.
     *
     */
    public function editAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $entity = $em->getRepository('CategoryBundle:Category')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Category entity.');
        }

        $editForm = $this->createEditForm($entity);
        $deleteForm = $this->createDeleteForm($id);

        return $this->render('CategoryBundle:Category:edit.html.twig', array(
            'entity'      => $entity,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    private function createEditForm(Category $entity)
    {
        $form = $this->createForm(new CategoryType(), $entity, array(
            'action' => $this->generateUrl('category_update', array('id' => $entity->getId())),
            'method' => 'PUT',
        ));

        $form->add('submit', 'submit', array('label' => 'Update'));

        return $form;
    }
    public function updateAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();

        $entity = $em->getRepository('CategoryBundle:Category')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Category entity.');
        }

        $deleteForm = $this->createDeleteForm($id);
        $editForm = $this->createEditForm($entity);
        $editForm->handleRequest($request);

        if ($editForm->isValid()) {
            $em->flush();

            return $this->redirect($this->generateUrl('category_edit', array('id' => $id)));
        }

        return $this->render('CategoryBundle:Category:edit.html.twig', array(
            'entity'      => $entity,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }
    /**
     * Deletes a Category entity.
     *
     */
    public function deleteAction(Request $request, $id)
    {
        $form = $this->createDeleteForm($id);
        $form->handleRequest($request);

        if ($form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $entity = $em->getRepository('CategoryBundle:Category')->find($id);

            if (!$entity) {
                throw $this->createNotFoundException('Unable to find Category entity.');
            }

            $em->remove($entity);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('category'));
    }

    /**
     * Creates a form to delete a Category entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm($id)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('category_delete', array('id' => $id)))
            ->setMethod('DELETE')
            ->add('submit', 'submit', array('label' => 'Delete'))
            ->getForm()
        ;
    }
}
