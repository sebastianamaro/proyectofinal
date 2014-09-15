<?php

namespace tuttifruttiweb\PlayerBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Player
 *
 * @ORM\Table()
 * @ORM\Entity
 */
class Player
{
    public function set($data) {
        if (!isset($data)) return;
        foreach ($data AS $key => $value) {
            switch ($key) {
                case 'dateInserted':
                    $dateInserted = \DateTime::createFromFormat('Y-m-d\TH:i:s.u\Z', $value);
                    $this->setDateInserted($dateInserted);
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
     * @ORM\Column(name="id", type="string", length=255)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $fbId;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=255)
     */
    private $name;
    /**
     * @var string
     *
     * @ORM\Column(name="email", type="string", length=255)
     */
    private $email;
    /**
     * @var string
     *
     * @ORM\Column(name="registrationId", type="string", length=255)
     */
    private $registrationId;
    /**
     * @var string
     *
     * @ORM\Column(name="dateInserted", type="string", length=255)
     */
    private $dateInserted;


    /**
     * Get fbId
     *
     * @return string 
     */
    public function getFbId()
    {
        return $this->fbId;
    }

    /**
     * Set name
     *
     * @param string $name
     * @return Player
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
     * Set email
     *
     * @param string $email
     * @return Player
     */
    public function setEmail($email)
    {
        $this->email = $email;

        return $this;
    }

    /**
     * Get email
     *
     * @return string 
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * Set registrationId
     *
     * @param string $registrationId
     * @return Player
     */
    public function setRegistrationId($registrationId)
    {
        $this->registrationId = $registrationId;

        return $this;
    }

    /**
     * Get registrationId
     *
     * @return string 
     */
    public function getRegistrationId()
    {
        return $this->registrationId;
    }

    /**
     * Set dateInserted
     *
     * @param string $dateInserted
     * @return Player
     */
    public function setDateInserted($dateInserted)
    {
        $this->dateInserted = $dateInserted;

        return $this;
    }

    /**
     * Get dateInserted
     *
     * @return string 
     */
    public function getDateInserted()
    {
        return $this->dateInserted;
    }
}
