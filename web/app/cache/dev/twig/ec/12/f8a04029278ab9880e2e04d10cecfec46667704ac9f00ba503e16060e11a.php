<?php

/* CategoryBundle:Category:show.html.twig */
class __TwigTemplate_ec12f8a04029278ab9880e2e04d10cecfec46667704ac9f00ba503e16060e11a extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("::base.html.twig");

        $this->blocks = array(
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

    // line 2
    public function block_body($context, array $blocks = array())
    {
        // line 3
        echo "<h1>Categor&iacute;a: <small>";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "name"), "html", null, true);
        echo "</small></h1> 
    <ol class=\"breadcrumb\">
      <li><a href=\"";
        // line 5
        echo $this->env->getExtension('routing')->getPath("category");
        echo "\"><i class=\"fa fa-desktop\"></i> Volver al listado Categor&iacute;as</a></li>
    </ol>
<h3>Detalle de la categor&iacute;a</h3>
<div class=\"row\">
    <div class=\"col-lg-6\">
        <div class=\"bs-example\">
            <div class=\"list-group\">
                <span class=\"list-group-item\"><b>Nombre:</b> ";
        // line 12
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "name"), "html", null, true);
        echo "</span>
                <span class=\"list-group-item\"><b>Es controlada:</b> ";
        // line 13
        if ($this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "isFixed")) {
            echo " Si ";
        } else {
            echo " No";
        }
        echo "</span>
                <span class=\"list-group-item\"><b>Fue denunciada:</b> ";
        // line 14
        if ($this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "isReported")) {
            echo " Si ";
        } else {
            echo " No";
        }
        echo "</span>
                <span class=\"list-group-item\"><b>Cantidad de usos:</b> ";
        // line 15
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "hits"), "html", null, true);
        echo "</span>
                <span class=\"list-group-item\"><b>Cantidad de palabras reportadas:</b> ";
        // line 16
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "reportedWordsAmount"), "html", null, true);
        echo "</span>
                
            </div>
        </div>
        ";
        // line 20
        echo twig_include($this->env, $context, "CategoryBundle:Category:words.html.twig", array("category" => (isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "path" => "category_show"));
        echo "
        
        <a href=\"";
        // line 22
        echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_edit", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
        echo "\"  class=\"btn btn-primary\">Editar</a>
        
        &nbsp;&nbsp;&nbsp;
        <a href=\"";
        // line 25
        echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_delete", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"))), "html", null, true);
        echo "\" class=\"btn btn-danger\">Eliminar</a>
    
        <br>    
    </div>
</div>
";
    }

    public function getTemplateName()
    {
        return "CategoryBundle:Category:show.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  84 => 15,  81 => 14,  34 => 23,  190 => 60,  185 => 59,  172 => 55,  167 => 53,  152 => 47,  134 => 41,  124 => 38,  113 => 34,  104 => 30,  77 => 21,  212 => 77,  198 => 71,  191 => 67,  174 => 62,  170 => 61,  150 => 56,  137 => 52,  129 => 33,  114 => 39,  110 => 33,  65 => 14,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 54,  140 => 53,  132 => 51,  128 => 49,  107 => 36,  61 => 5,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 61,  143 => 56,  135 => 53,  119 => 30,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  28 => 2,  87 => 25,  93 => 28,  88 => 16,  78 => 20,  46 => 7,  38 => 6,  26 => 11,  44 => 37,  31 => 3,  201 => 92,  196 => 63,  183 => 65,  171 => 61,  166 => 60,  163 => 51,  158 => 58,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 37,  121 => 46,  117 => 44,  105 => 24,  91 => 27,  62 => 16,  49 => 8,  94 => 19,  89 => 25,  85 => 25,  75 => 12,  68 => 15,  56 => 9,  27 => 4,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  79 => 21,  72 => 17,  69 => 18,  47 => 12,  40 => 8,  37 => 5,  22 => 2,  246 => 90,  157 => 48,  145 => 46,  139 => 38,  131 => 48,  123 => 47,  120 => 40,  115 => 43,  111 => 37,  108 => 25,  101 => 32,  98 => 31,  96 => 28,  83 => 22,  74 => 14,  66 => 15,  55 => 10,  52 => 21,  50 => 10,  43 => 9,  41 => 36,  35 => 6,  32 => 4,  29 => 12,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 64,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 58,  144 => 44,  141 => 39,  133 => 55,  130 => 39,  125 => 32,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 36,  103 => 32,  99 => 29,  95 => 28,  92 => 27,  86 => 25,  82 => 22,  80 => 19,  73 => 19,  64 => 17,  60 => 6,  57 => 4,  54 => 3,  51 => 13,  48 => 1,  45 => 17,  42 => 7,  39 => 30,  36 => 29,  33 => 4,  30 => 5,);
    }
}
