<?php
namespace tuttifruttiweb\PlayerBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;

class PlayerFilterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name',null,array(
                'label'  => 'Nombre',
                'required' => false,
                'attr'=>array('class'=>'form-control')))
            ->add('fbId',null,array(
                'label'  => 'Identificador Facebook',
                'required' => false,
                'attr'=>array('class'=>'form-control')))
            ->add('email',null,array(
                'label'  => 'Email',
                'required' => false,
                'attr'=>array('class'=>'form-control')))
            ;
            
    }

    public function getName()
    {
        return 'item_filter';
    }

    public function setDefaultOptions(OptionsResolverInterface $resolver)
    {
        $resolver->setDefaults(array(
            'csrf_protection'   => false,
            'validation_groups' => array('filtering') // avoid NotBlank() constraint-related message
        ));
    }
}