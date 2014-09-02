<?php

namespace tuttifruttiweb\UtilsBundle\Utils;

class ArraySorter
{
    private $sort;
    private $direction;

    function __construct( $sort, $direction = 'asc' ) {
        $this->sort = $sort;
        $this->direction = $direction;
    }
    public function compare($a, $b) 
    {
        $sort = 'get'.ucfirst($this->sort);
        $reflectionMethod = new \ReflectionMethod($a, $sort);

        if ($this->direction == 'asc' )
            return strcmp($reflectionMethod->invoke($a), $reflectionMethod->invoke($b));
        else
            return strcmp($reflectionMethod->invoke($b), $reflectionMethod->invoke($a));

    }
    public static function sort($array, $sort, $direction = 'asc'){
        if (!is_array($array))
            return array();
        usort($array,  array(new ArraySorter($sort,$direction), "compare"));
        return $array;
    }
}