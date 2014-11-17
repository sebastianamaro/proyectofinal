<?php

namespace tuttifruttiweb\UtilsBundle\Utils;

use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;

class Encoder
{
	public function getSerializedObject($object){
		$encoders = array(new JsonEncoder());
        $normalizers = array(new GetSetMethodNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $jsonObject = $serializer->serialize($object, 'json');
        return json_decode($jsonObject);
	}
	public function getSerializedCategory($category){
		$encoders = array(new JsonEncoder());
        $normalizers = array(new GetSetMethodNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $acceptedWords = array();
        foreach ($category->getAcceptedWords() as $word) {
        	$acceptedWords[] = $word->getValue();
        }
        $categoryJson = array('name'=>$category->getName(),
        	'isFixed'=>$category->getIsFixed(),
        	'isReported'=>$category->getIsReported(),
        	'acceptedWords'=> $acceptedWords
        	);

        $jsonObject = $serializer->serialize($categoryJson, 'json');
        return json_decode($jsonObject);
	}
}