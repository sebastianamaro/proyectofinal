<?php

namespace tuttifruttiweb\CategoryBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

use tuttifruttiweb\CategoryBundle\Entity\Word;
/**
 * Word
 *
 * @ORM\Table()
 * @ORM\Entity
 */
class ReportedWord extends Word
{
	public function __construct($value=''){
		if (is_object($value)){
        	$this->count = $value->count;
        }
        parent::__construct($value);
    }
    
    /**
    * @ORM\ManyToOne(targetEntity="\tuttifruttiweb\CategoryBundle\Entity\Category", inversedBy="reportedWords")
    * @ORM\JoinColumn(name="idCategory", referencedColumnName="id")
    */
    protected $category;

    /**
     * @var string
     *
     * @ORM\Column(name="count", type="integer")
     */
    protected $count;
    public function setCount($count)
    {
        $this->count = $count;
        return $this;
    }

    public function getCount()
    {
        return $this->count;
    }
}
