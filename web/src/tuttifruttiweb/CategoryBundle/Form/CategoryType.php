<?php

namespace tuttifruttiweb\CategoryBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;

class CategoryType extends AbstractType
{
    /**
    * @param FormBuilderInterface $builder
    * @param array $options
    */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name',null,array(
                'label'  => 'Nombre',
                'required' => true,
                'attr'=>array('class'=>'form-control')))
            ->add('isFixed', 'choice', array(
                'label'  => 'Es controlada',
                'choices'   => array(true => 'Si', false => 'No'),
                'required'  => true,
                'expanded' =>true,
                'multiple' => false
            ))
            ->add('acceptedWords','collection',
                array(
                'type'=>new AcceptedWordType(),
                'label'=>false,
                'allow_add'=>true,
                'allow_delete'=>true))
            ->add('submit','submit',array('label' => 'Guardar',
                'attr'=> array('class'=>'btn btn-primary btn-lg')))
            ;
    }
    
    /**
     * @param OptionsResolverInterface $resolver
     */
    public function setDefaultOptions(OptionsResolverInterface $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'tuttifruttiweb\CategoryBundle\Entity\Category'
        ));
    }

    /**
     * @return string
     */
    public function getName()
    {
        return 'tuttifruttiweb_ca';
    }
}
