<?php

/* CategoryBundle:Category:index.html.twig */
class __TwigTemplate_1e84bba37b01593faba90ecc06df4fbd81417ef6b42c0b1422c83417bcfe4ba1 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("::base.html.twig");

        $this->blocks = array(
            'javascriptsHead' => array($this, 'block_javascriptsHead'),
            'body' => array($this, 'block_body'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "::base.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 4
        echo "  <script src=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/js/agregarLinkPaginadores.js"), "html", null, true);
        echo "\"></script>
";
    }

    // line 7
    public function block_body($context, array $blocks = array())
    {
        // line 8
        echo "<h1>Categor&iacute;as </h1>  
<ol class=\"breadcrumb\">
    <li><a href=\"";
        // line 10
        echo $this->env->getExtension('routing')->getPath("category_create");
        echo "\">Crear nueva categoría</a></li>
</ol> 
<form method=\"get\" action=\".\">
<div class=\"table-responsive\">
    <table class=\"table\">
        <thead>
            <tr>
                <th><b>";
        // line 17
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "name"), 'label');
        echo "</b></th>
                <th><b>";
        // line 18
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "isFixed"), 'label');
        echo "</b></th>
                <th><b>";
        // line 19
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "isReported"), 'label');
        echo "</b></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>";
        // line 24
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "name"), 'widget');
        echo "</td> 
                <td>";
        // line 25
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "isFixed"), 'widget');
        echo "</td> 
                <td>";
        // line 26
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "isReported"), 'widget');
        echo "</td> 
            </tr>
        </tbody>                        
            <tr>
                <td></td>
                <td align='center'><input type=\"submit\" class=\"btn btn-primary\" name=\"submit-filter\" value=\"Buscar\" /></td>
                <td align='center'> <input type=\"submit\" class=\"btn btn-primary\" name=\"clear-filter\" value=\"Blanquear\" /></td>
                <td></td>                        
            </tr>       
    </table>
</div>
";
        // line 37
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'form_end');
        echo "
<center><ol class=\"breadcrumb\">
    <li>";
        // line 39
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["categories"]) ? $context["categories"] : $this->getContext($context, "categories"))), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, (isset($context["totalCategories"]) ? $context["totalCategories"] : $this->getContext($context, "totalCategories")), "html", null, true);
        echo " categor&iacute;as</li>
</ol> </center>

<br>
<div class=\"table-responsive\">
  <table class=\"table table-bordered table-hover table-striped tablesorter\">
    <thead>
      <tr>

        <th>Nombre</th>
        <th>Es Controlada</th>
        <th>Fue Denunciada</th>
        <th>Cantidad de Palabras Aceptadas</th>
        <th>Cantidad de Palabras Reportadas</th>
        <th>Cantidad de usos</th>
        <th>Acciones</th>

      </tr>
    </thead>
    <tbody> 
    ";
        // line 59
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["categories"]) ? $context["categories"] : $this->getContext($context, "categories")));
        foreach ($context['_seq'] as $context["_key"] => $context["category"]) {
            // line 60
            echo "            <tr>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 61
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "name"), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 62
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            if ($this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "isFixed")) {
                echo " Si ";
            } else {
                echo " No";
            }
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 63
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            if ($this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "isReported")) {
                echo " Si ";
            } else {
                echo " No";
            }
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 64
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "acceptedWords")), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 65
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "reportedWords")), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 66
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "hits"), "html", null, true);
            echo "</td>
                <td>
                    <a href=\"";
            // line 68
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_edit", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "\" class=\"btn btn-primary btn-xs\">editar</a>
                    <a href=\"";
            // line 69
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_delete", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
            echo "\" class=\"btn btn-danger btn-xs\">eliminar</a>
                </td>
            </tr>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['category'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 73
        echo "    </tbody>
  </table>
</div>
<div class=\"pagination\">
    ";
        // line 77
        echo $this->env->getExtension('simple_paginator_extension')->render("category", null, array("container_class" => "pagination", "previousPageText" => "Anterior", "nextPageText" => "Siguiente", "currentClass" => "active", "firstPageText" => "Primera", "lastPageText" => "Última", "firstDisabledClass" => "disabled", "lastDisabledClass" => "disabled", "nextDisabledClass" => "disabled", "previousDisabledClass" => "disabled"));
        // line 88
        echo "
</div>
";
    }

    public function getTemplateName()
    {
        return "CategoryBundle:Category:index.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  198 => 88,  196 => 77,  190 => 73,  180 => 69,  176 => 68,  169 => 66,  163 => 65,  157 => 64,  147 => 63,  137 => 62,  131 => 61,  128 => 60,  124 => 59,  99 => 39,  94 => 37,  80 => 26,  76 => 25,  72 => 24,  64 => 19,  60 => 18,  56 => 17,  46 => 10,  42 => 8,  39 => 7,  32 => 4,  29 => 3,);
    }
}
