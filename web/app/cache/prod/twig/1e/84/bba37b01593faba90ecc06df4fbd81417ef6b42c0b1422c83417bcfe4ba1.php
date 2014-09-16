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
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "name"), 'label');
        echo "</b></th>
                <th><b>";
        // line 18
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "isFixed"), 'label');
        echo "</b></th>
                <th><b>";
        // line 19
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "isReported"), 'label');
        echo "</b></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>";
        // line 24
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "name"), 'widget');
        echo "</td> 
                <td>";
        // line 25
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "isFixed"), 'widget');
        echo "</td> 
                <td>";
        // line 26
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "isReported"), 'widget');
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
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : null), 'form_end');
        echo "
<center><ol class=\"breadcrumb\">
    <li>";
        // line 39
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["categories"]) ? $context["categories"] : null)), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, (isset($context["totalCategories"]) ? $context["totalCategories"] : null), "html", null, true);
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
        $context['_seq'] = twig_ensure_traversable((isset($context["categories"]) ? $context["categories"] : null));
        foreach ($context['_seq'] as $context["_key"] => $context["category"]) {
            // line 60
            echo "            <tr>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 61
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "name"), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 62
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            if ($this->getAttribute((isset($context["category"]) ? $context["category"] : null), "isFixed")) {
                echo " Si ";
            } else {
                echo " No";
            }
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 63
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            if ($this->getAttribute((isset($context["category"]) ? $context["category"] : null), "isReported")) {
                echo " Si ";
            } else {
                echo " No";
            }
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 64
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "acceptedWords")), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 65
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "reportedWords")), "html", null, true);
            echo "</td>
                <td style=\"cursor:pointer;\"  onclick=\"location.href='";
            // line 66
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_show", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "'\">";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "hits"), "html", null, true);
            echo "</td>
                <td>
                    <a href=\"";
            // line 68
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_edit", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
            echo "\" class=\"btn btn-primary btn-xs\">editar</a>
                    <a href=\"";
            // line 69
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("category_delete", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : null), "id"))), "html", null, true);
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
        return array (  180 => 69,  175 => 70,  100 => 38,  76 => 25,  74 => 25,  70 => 19,  83 => 22,  37 => 5,  84 => 15,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 55,  167 => 53,  157 => 64,  152 => 47,  139 => 17,  134 => 41,  124 => 59,  113 => 46,  104 => 45,  96 => 38,  77 => 21,  212 => 77,  198 => 88,  191 => 67,  174 => 62,  170 => 64,  150 => 5,  137 => 62,  129 => 33,  114 => 39,  110 => 33,  65 => 21,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 64,  128 => 60,  111 => 44,  107 => 43,  61 => 5,  273 => 96,  269 => 94,  254 => 92,  246 => 90,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 53,  131 => 61,  119 => 30,  108 => 46,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  47 => 12,  28 => 2,  87 => 35,  55 => 13,  98 => 31,  93 => 28,  88 => 16,  78 => 26,  46 => 10,  43 => 9,  40 => 8,  38 => 7,  26 => 11,  44 => 37,  31 => 3,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 63,  142 => 18,  138 => 54,  136 => 16,  123 => 47,  121 => 48,  117 => 47,  115 => 43,  105 => 40,  91 => 36,  69 => 23,  66 => 18,  62 => 16,  49 => 8,  32 => 4,  101 => 32,  94 => 37,  89 => 25,  85 => 25,  79 => 21,  75 => 12,  72 => 24,  68 => 15,  56 => 17,  50 => 10,  41 => 36,  27 => 4,  22 => 2,  35 => 6,  29 => 3,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 68,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 55,  130 => 39,  125 => 15,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 33,  103 => 32,  99 => 39,  95 => 28,  92 => 37,  86 => 25,  82 => 22,  80 => 26,  73 => 24,  64 => 19,  60 => 18,  57 => 4,  54 => 3,  51 => 10,  48 => 11,  45 => 8,  42 => 8,  39 => 7,  36 => 29,  33 => 4,  30 => 5,);
    }
}
