<?php

namespace tuttifruttiweb\CategoryBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Word
 *
 * @ORM\Table()
 * @ORM\Entity
 */
class Word
{
    public function __construct($value=''){
        $this->value = $value;
    }
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="value", type="string", length=255)
     */
    private $value;

    
    /**
    * @ORM\ManyToOne(targetEntity="\tuttifruttiweb\CategoryBundle\Entity\Category")
    * @ORM\JoinColumn(name="idCategory", referencedColumnName="id")
    */
    private $category;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set value
     *
     * @param string $value
     * @return Word
     */
    public function setValue($value)
    {
        $this->value = $value;

        return $this;
    }

    /**
     * Get value
     *
     * @return string 
     */
    public function getValue()
    {
        return $this->value;
    }

    /**
     * Set category
     *
     * @param \tuttifruttiweb\CategoryBundle\Entity\Category $category
     * @return Word
     */
    public function setCategory(\tuttifruttiweb\CategoryBundle\Entity\Category $category = null)
    {
        $this->category = $category;

        return $this;
    }

    /**
     * Get category
     *
     * @return \tuttifruttiweb\CategoryBundle\Entity\Category 
     */
    public function getCategory()
    {
        return $this->category;
    }
}
