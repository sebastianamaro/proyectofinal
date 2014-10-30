<?php

namespace tuttifruttiweb\CategoryBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;
use tuttifruttiweb\CategoryBundle\Form\WordType;

class ReportedWordType extends WordType
{
    public function setDefaultOptions(OptionsResolverInterface $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'tuttifruttiweb\CategoryBundle\Entity\ReportedWord'
        ));
    }
    public function getName()
    {
        return 'tuttifruttiweb_categorybundle_reportedword';
    }
}
