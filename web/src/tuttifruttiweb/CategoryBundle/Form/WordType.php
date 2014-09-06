<?php

namespace tuttifruttiweb\CategoryBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;

abstract class WordType extends AbstractType
{
    /**
    * @param FormBuilderInterface $builder
    * @param array $options
    */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('value',null,array(
                'label'  => false,
                'required' => true,
                'attr'=>array('class'=>'form-control',
                        'oninvalid' => "setCustomValidity('Ingrese una palabra')",
                        'onchange' => "try{setCustomValidity('')}catch(e){}"
                    )
                    ));
    }
    
}
