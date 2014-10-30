<?php
namespace tuttifruttiweb\CategoryBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;

class CategoryFilterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name',null,array(
                'label'  => 'Nombre',
                'required' => false,
                'attr'=>array('class'=>'form-control')))
            ->add('isFixed', 'choice', array(
                'label'  => 'Tipo de categorías',
                'choices'   => array(null => 'Todas', '0' => 'Libres', '1'=>'Controladas'),
                'required'  => false,
                'attr'=>array('class'=>'form-control')))
            ->add('isReported', 'choice', array(
                'label'  => 'Fue reportada',
                'choices'   => array(null => 'Todas', '0' => 'No', '1'=>'Si'),
                'required'  => false,
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