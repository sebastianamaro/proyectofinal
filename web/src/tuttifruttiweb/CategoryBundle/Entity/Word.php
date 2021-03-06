<?php

namespace tuttifruttiweb\CategoryBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use tuttifruttiweb\UtilsBundle\Utils\StringModifier;

/**
 * Word
 *
 * @ORM\Table()
 * @ORM\Entity
 */
class Word
{
    public function __construct($value=''){
        if (is_object($value)){
            $value = $value->word;
        }
        $str = new StringModifier();
        $this->value = $str->removeAccents(strtoupper($value));
    }
    /**
     * @var integer
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;

    /**
     * @var string
     *
     * @ORM\Column(name="value", type="string", length=255)
     */
    protected $value;

    public function getId()
    {
        return $this->id;
    }
    public function getCategory()
    {
        return $this->category;
    }
    public function setValue($value)
    {
        $this->value = $value;

        return $this;
    }

    public function getValue()
    {
        return $this->value;
    }

    
}
