<?php

namespace tuttifruttiweb\StatisticsBundle\Controller;

use Lsw\ApiCallerBundle\Call\HttpGetJson;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use tuttifruttiweb\UtilsBundle\Utils\ArraySorter as ArraySorter;
use tuttifruttiweb\CategoryBundle\Entity\Category;


class DefaultController extends Controller
{
    public function indexAction()
    {
      	$usedCategoriesPerType = $this->getUsedCategoriesPerType();
      	$mostUsedNonFixedCategories = $this->getMostUsedNonFixedCategories();
        $playersInTime = $this->getPlayersInTime();
        return $this->render('StatisticsBundle:Default:index.html.twig',
        	array('mostUsedNonFixedCategories'=>$mostUsedNonFixedCategories,
        		'usedCategoriesPerType'=>$usedCategoriesPerType,
            'playersInTime'=>$playersInTime));
    }
    private function getPlayersInTime(){
      $url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.player').'/createdPerDate';
      $playersData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      if ($playersData == null){
        $playersData = array();
      }
      $usedCategoriesPerType = array();
      foreach ($playersData as $key => $playerData) {
        $month = (strlen($playerData->_id->month) == 1) ?'0'.$playerData->_id->month : $playerData->_id->month;
        
        $date = $playerData->_id->year.'-'.$month.'-'.$playerData->_id->day;
        $playersInTime[] = array('date'=> $date, 'amount'=>$playerData->amount);
      }
      return $playersInTime;
      
    }
    private function getUsedCategoriesPerType(){
		$url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'/hitsPerType';
        $categoriesData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      	if ($categoriesData == null){
	        $categoriesData = array();
    	}

      	$usedCategoriesPerType = array();
      	foreach ($categoriesData as $key => $categoryData) {
      		$type = ($categoryData->_id == true) ? "Controladas" : "No Controladas";
          	$usedCategoriesPerType[] = array('type'=> $type, 'hits'=>$categoryData->hits);
      	}
      	return $usedCategoriesPerType;
    }
    private function getMostUsedNonFixedCategories(){

    	$url = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.category').'?isFixed=0';
        $categoriesData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      	if ($categoriesData == null){
	        $categoriesData = array();
    	}
      	$categories = array();
      	$cantidad = 0;
      	foreach ($categoriesData as $key => $categoryData) {
      		$cantidad ++;
      		if ($cantidad > 10){
      			break;
      		}
          	$category = new Category();
          	$category->set($categoryData);
          	if ($category->getId()){
            	$categories[] = $category;
          	}
      	}
      
      	$sort = $this->get('request')->query->get('sort','name');
      	$direction = $this->get('request')->query->get('direction', 'DESC');
      	return ArraySorter::sort($categories,'hits','DESC');
    }
}
