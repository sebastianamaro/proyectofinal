<?php

namespace tuttifruttiweb\CategoryBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use FOS\RestBundle\Controller\Annotations as Rest;
use Doctrine\Common\Collections\ArrayCollection;
use Lsw\ApiCallerBundle\Call\HttpGetJson;
use Lsw\ApiCallerBundle\Call\HttpPostJson;
use Lsw\ApiCallerBundle\Call\HttpPutJson;
use Lsw\ApiCallerBundle\Call\HttpDeleteJson;
use Lsw\ApiCallerBundle\Caller\LoggingApiCaller;
use Symfony\Component\HttpFoundation\Response;

use tuttifruttiweb\UtilsBundle\Utils\ArraySorter as ArraySorter;
use tuttifruttiweb\UtilsBundle\Utils\Encoder as Encoder;
use tuttifruttiweb\CategoryBundle\Entity\Category;
use tuttifruttiweb\CategoryBundle\Entity\AcceptedWord;
use tuttifruttiweb\CategoryBundle\Form\CategoryType;
use tuttifruttiweb\CategoryBundle\Form\AcceptedWordType;
use tuttifruttiweb\CategoryBundle\Form\ReportedWordType;
use tuttifruttiweb\CategoryBundle\Form\CategoryFilterType;

class CategoryController extends Controller
{

    public function indexAction(Request $request)
    {
      $sort='name';
      $direction='ASC';
      $initialUrl = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'?';
        
      $form = $this->get('form.factory')->create(new CategoryFilterType(),
          null, array(
          'method' => 'get'
      ));
      $url = $initialUrl;
      if ($this->get('request')->query->has('submit-filter')) {
        $form->handleRequest($request);
        foreach ($request->query->get('item_filter') as $key=>$filter) {
          if ($filter !== ''){
            if ($url !== $initialUrl) $url .=  '&';
            $url .= $key.'='.$filter;
          }
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
          if ($category->getId()){
            $categories[] = $category;
          }
      }
      
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
          'form' => $form->createView(),
          'sort'=>$sort,
          'direction'=>$direction
      ));
    }
    public function createAction(Request $request)
    {
        $category = new Category();
        $acceptedWord = new AcceptedWord();
        $category->addAcceptedWord($acceptedWord);
        $form = $this->createCreateForm($category);
        $form->handleRequest($request);

        if ($form->isValid()) {
            $acceptedWordsBatch = $form["acceptedWordsBatch"]->getData();
            $category->addAcceptedWordsBatch($acceptedWordsBatch);
            $encoder = new Encoder();
            $objectCategory = $encoder->getSerializedCategory($category);
            $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category');            
            $reqJson  = new HttpPostJson($url,$objectCategory);
            $categoryData = $this->get('api_caller')->call($reqJson);

            if (!is_object($categoryData) || !$categoryData->id){
              throw new \Exception("Error al guardar la categoría", 1);
            }
            return $this->redirect($this->generateUrl('category_show', array('id' => $categoryData->id)));
        }

        return $this->render('CategoryBundle:Category:new.html.twig', array(
            'category' => $category,
            'form'   => $form->createView(),
        ));
    }

    private function createCreateForm(Category $entity)
    {
        $form = $this->createForm(new CategoryType(), $entity, array(
            'action' => $this->generateUrl('category_create'),
            'method' => 'POST',
        ));

        return $form;
    }
    public function acceptReportedWordAction($id,$word){
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
      $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      $category = new Category();
      $category->set($categoryData);
      if (!$category->getId()){
        throw $this->createNotFoundException('No se encuentra la categoría.');
      }
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id.'/word/'.$word;            
      $reqJson  = new HttpPutJson($url,array());
      $categoryData = $this->get('api_caller')->call($reqJson);
      return $this->redirect($this->generateUrl('category_show', array('id' => $id)));
    }
    public function rejectReportedWordAction($id,$word){
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
      $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      $category = new Category();
      $category->set($categoryData);
      if (!$category->getId()){
        throw $this->createNotFoundException('No se encuentra la categoría.');
      }
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id.'/word/'.$word.'/reported';            
      $reqJson  = new HttpDeleteJson($url,array());
      $categoryData = $this->get('api_caller')->call($reqJson);
      return $this->redirect($this->generateUrl('category_show', array('id' => $id)));
    }
    public function removeAcceptedWordAction($id,$word){
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
      $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      $category = new Category();
      $category->set($categoryData);
      if (!$category->getId()){
        throw $this->createNotFoundException('No se encuentra la categoría.');
      }
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id.'/word/'.$word;            
      $reqJson  = new HttpDeleteJson($url,array());
      $categoryData = $this->get('api_caller')->call($reqJson);
      return $this->redirect($this->generateUrl('category_show', array('id' => $id)));
    }
    public function showAction($id)
    {
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
      $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      $category = new Category();
      $category->set($categoryData);
      if (!$category->getId()){
        throw $this->createNotFoundException('No se encuentra la categoría.');
      }
      
      $paginator = $this->get('ideup.simple_paginator');

      $reportedWords = $category->getReportedWords()->toArray();
      $reportedWords =  ArraySorter::sort($reportedWords,'value','ASC');
      $reportedWordsPage = $paginator
            ->setItemsPerPage(5,'reportedWords')
            ->paginate($reportedWords, 'reportedWords')
            ->getResult();
  
      $acceptedWords = $category->getAcceptedWords()->toArray();
      $acceptedWords =  ArraySorter::sort($acceptedWords,'value','ASC');
      $acceptedWordsPage = $paginator
            ->setItemsPerPage(5,'acceptedWords')
            ->paginate($acceptedWords, 'acceptedWords')
            ->getResult();

      return $this->render('CategoryBundle:Category:show.html.twig', array(
          'category'      => $category,
          'reportedWordsPage' =>$reportedWordsPage,
          'acceptedWordsPage' =>$acceptedWordsPage,
      ));
    }
    public function editAction(Request $request, $id)
    {
        $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
        $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
        $category = new Category();
        $category->set($categoryData);
        if (!$category->getId()){
          throw $this->createNotFoundException('No se encuentra la categoría.');
        }
        if (count($category->getAcceptedWords()) == 0){
          $acceptedWord = new AcceptedWord();
          $category->addAcceptedWord($acceptedWord);
        }
        $editForm = $this->createEditForm($category);
        $editForm->handleRequest($request);
        if ($editForm->isValid()) {
          $acceptedWordsBatch = $editForm["acceptedWordsBatch"]->getData();
          $category->addAcceptedWordsBatch($acceptedWordsBatch);
          $encoder = new Encoder();
          $objectCategory = $encoder->getSerializedCategory($category);

          $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;            
          $reqJson  = new HttpPutJson($url,$objectCategory);
          $categoryData = $this->get('api_caller')->call($reqJson);
          return $this->redirect($this->generateUrl('category_show', array('id' => $category->getId())));
        }
        return $this->render('CategoryBundle:Category:edit.html.twig', array(
            'category'      => $category,
            'edit_form'   => $editForm->createView()
        ));
    }

    private function createEditForm(Category $entity)
    {
        $form = $this->createForm(new CategoryType(), $entity, array(
            'action' => $this->generateUrl('category_edit', array('id' => $entity->getId())),
            'method' => 'PUT',
        ));
        $form->add('isReported', 'choice', array(
                'label'  => 'Está reportada',
                'choices'   => array(true => 'Si', false => 'No'),
                'required'  => true,
                'expanded' =>true,
                'multiple' => false
            ));
        return $form;
    }
    public function deleteAction(Request $request, $id)
    {
        $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
        $categoryData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
        $category = new Category();
        $category->set($categoryData);
        if (!$category->getId()){
          throw $this->createNotFoundException('No se encuentra la categoría.');
        }
        $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/'.$id;
        $this->get('api_caller')->call(new HttpDeleteJson($url,array()));
        return $this->redirect($this->generateUrl('category'));
    }
}
