<?php

namespace tuttifruttiweb\CategoryBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Category
 *
 * @ORM\Table()
 * @ORM\Entity
 */
class Category
{
    public function set($data) {
        if (is_null($data)) return;
        foreach ($data AS $key => $value) {
            switch ($key) {
                case 'acceptedWords':
                    foreach ($value as $keyWord => $valueWord) {
                        $word = new Word($valueWord); 
                        $this->addAcceptedWord($word);
                    }
                    break;
                case 'reportedWords':
                    foreach ($value as $keyWord => $valueWord) {
                        $word = new Word($valueWord); 
                        $this->addReportedWord($word);
                    }
                    break;
                default:
                    $this->{$key} = $value;
                    break;
            }
        }
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
     * @ORM\Column(name="name", type="string", length=255)
     */
    public $name;

    /**
     * @var boolean
     *
     * @ORM\Column(name="isFixed", type="boolean")
     */
    private $isFixed;

    /**
     * @var boolean
     *
     * @ORM\Column(name="isReported", type="boolean")
     */
    private $isReported;
    
    /**
     * @var integer
     *
     * @ORM\Column(name="hits", type="integer")
     */
    private $hits = 0;

    /**
    * @ORM\OneToMany(targetEntity="\tuttifruttiweb\CategoryBundle\Entity\Word", mappedBy="category", cascade={"all"}, orphanRemoval=true)
     */
    private $reportedWords;
    /**
    * @ORM\OneToMany(targetEntity="\tuttifruttiweb\CategoryBundle\Entity\Word", mappedBy="category", cascade={"all"}, orphanRemoval=true)
     */
    private $acceptedWords;


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
     * Set name
     *
     * @param string $name
     * @return Category
     */
    public function setName($name)
    {
        $this->name = $name;

        return $this;
    }

    /**
     * Get name
     *
     * @return string 
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Set isFixed
     *
     * @param boolean $isFixed
     * @return Category
     */
    public function setIsFixed($isFixed)
    {
        $this->isFixed = $isFixed;

        return $this;
    }

    /**
     * Get isFixed
     *
     * @return boolean 
     */
    public function getIsFixed()
    {
        return $this->isFixed;
    }

    /**
     * Set isReported
     *
     * @param boolean $isReported
     * @return Category
     */
    public function setIsReported($isReported)
    {
        $this->isReported = $isReported;

        return $this;
    }

    /**
     * Get isReported
     *
     * @return boolean 
     */
    public function getIsReported()
    {
        return $this->isReported;
    }

    /**
     * Get reportedWordsAmount
     *
     * @return integer 
     */
    public function getReportedWordsAmount()
    {
        return count($this->getReportedWords());
    }

    /**
     * Set hits
     *
     * @param integer $hits
     * @return Category
     */
    public function setHits($hits)
    {
        $this->hits = $hits;

        return $this;
    }

    /**
     * Get hits
     *
     * @return integer 
     */
    public function getHits()
    {
        return $this->hits;
    }
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->acceptedWords = new \Doctrine\Common\Collections\ArrayCollection();
        $this->reportedWords = new \Doctrine\Common\Collections\ArrayCollection();
    }


    /**
     * Add reportedWords
     *
     * @param \tuttifruttiweb\CategoryBundle\Entity\Word $reportedWords
     * @return Category
     */
    public function addReportedWord(\tuttifruttiweb\CategoryBundle\Entity\Word $reportedWords)
    {
        $this->reportedWords[] = $reportedWords;

        return $this;
    }

    /**
     * Remove reportedWords
     *
     * @param \tuttifruttiweb\CategoryBundle\Entity\Word $reportedWords
     */
    public function removeReportedWord(\tuttifruttiweb\CategoryBundle\Entity\Word $reportedWords)
    {
        $this->reportedWords->removeElement($reportedWords);
    }

    /**
     * Get reportedWords
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getReportedWords()
    {
        return $this->reportedWords;
    }

    /**
     * Add acceptedWords
     *
     * @param \tuttifruttiweb\CategoryBundle\Entity\Word $acceptedWords
     * @return Category
     */
    public function addAcceptedWord(\tuttifruttiweb\CategoryBundle\Entity\Word $acceptedWords)
    {
        $this->acceptedWords[] = $acceptedWords;

        return $this;
    }

    /**
     * Remove acceptedWords
     *
     * @param \tuttifruttiweb\CategoryBundle\Entity\Word $acceptedWords
     */
    public function removeAcceptedWord(\tuttifruttiweb\CategoryBundle\Entity\Word $acceptedWords)
    {
        $this->acceptedWords->removeElement($acceptedWords);
    }

    /**
     * Get acceptedWords
     *
     * @return \Doctrine\Common\Collections\Collection 
     */
    public function getAcceptedWords()
    {
        return $this->acceptedWords;
    }
}
