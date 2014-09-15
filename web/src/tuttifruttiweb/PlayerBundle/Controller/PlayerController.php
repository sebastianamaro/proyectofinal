<?php

namespace tuttifruttiweb\PlayerBundle\Controller;

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
use tuttifruttiweb\PlayerBundle\Entity\Player;
use tuttifruttiweb\PlayerBundle\Form\PlayerFilterType;

class PlayerController extends Controller
{
    public function indexAction(Request $request)
    {
      $sort='name';
      $direction='ASC';
      $initialUrl = $this->container->getParameter('server.location').'/'.$this->container->getParameter('server.player').'?';
        
      $form = $this->get('form.factory')->create(new PlayerFilterType(),
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
      $playersData = $this->get('api_caller')->call(new HttpGetJson($url,array()));
      if ($playersData == null){
        $playersData = array();
      }
      $players = array();
      foreach ($playersData as $key => $playerData) {
          $player = new Player();
          $player->set($playerData);
          $players[] = $player;
      }
      
      $players =  ArraySorter::sort($players,$sort,$direction);
      $totalPlayers = count($players);

      $paginator = $this->get('ideup.simple_paginator');
      $playersPage = $paginator
            ->setItemsPerPage(10)
            ->paginate($players)
            ->getResult();

      return $this->render('PlayerBundle:Player:index.html.twig', array(
          'players' => $playersPage,
          'totalPlayers'=>$totalPlayers,
          'form' => $form->createView(),
          'sort'=>$sort,
          'direction'=>$direction
      ));
    }
}
