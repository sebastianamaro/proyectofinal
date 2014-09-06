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
class AcceptedWord extends Word
{
    
    /**
    * @ORM\ManyToOne(targetEntity="\tuttifruttiweb\CategoryBundle\Entity\Category", inversedBy="acceptedWords")
    * @ORM\JoinColumn(name="idCategory", referencedColumnName="id")
    */
    protected $category;
}
