<?php

/* PlayerBundle:Player:index.html.twig */
class __TwigTemplate_ec678aec37a541567007856808458dd240d475590d07116a96f615e93f1013b2 extends Twig_Template
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
        echo "<h1>Jugadores</h1>  

<form method=\"get\" action=\".\">
<div class=\"table-responsive\">
    <table class=\"table\">
        <thead>
            <tr>
                <th><b>";
        // line 15
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "name"), 'label');
        echo "</b></th>
                <th><b>";
        // line 16
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "email"), 'label');
        echo "</b></th>
                <th><b>";
        // line 17
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "fbId"), 'label');
        echo "</b></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>";
        // line 22
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "name"), 'widget');
        echo "</td> 
                <td>";
        // line 23
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "email"), 'widget');
        echo "</td> 
                <td>";
        // line 24
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : null), "fbId"), 'widget');
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
        // line 35
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : null), 'form_end');
        echo "
<center><ol class=\"breadcrumb\">
    <li>";
        // line 37
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["players"]) ? $context["players"] : null)), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, (isset($context["totalPlayers"]) ? $context["totalPlayers"] : null), "html", null, true);
        echo " jugadores</li>
</ol> </center>

<br>
<div class=\"table-responsive\">
  <table class=\"table table-bordered table-hover table-striped tablesorter\">
    <thead>
      <tr>

        <th>Nombre</th>
        <th>Email</th>
        <th>Id. Facebook</th>
        <th>Fecha de alta</th>
        
      </tr>
    </thead>
    <tbody> 
    ";
        // line 54
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["players"]) ? $context["players"] : null));
        foreach ($context['_seq'] as $context["_key"] => $context["player"]) {
            // line 55
            echo "            <tr>
                <td>";
            // line 56
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : null), "name"), "html", null, true);
            echo "</td>
                <td>";
            // line 57
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : null), "email"), "html", null, true);
            echo "</td>
                <td>";
            // line 58
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : null), "fbId"), "html", null, true);
            echo "</td>
                <td>";
            // line 59
            echo twig_escape_filter($this->env, twig_date_format_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : null), "dateInserted"), "d/m/Y"), "html", null, true);
            echo "</td>
                
            </tr>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['player'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 63
        echo "    </tbody>
  </table>
</div>
<div class=\"pagination\">
    ";
        // line 67
        echo $this->env->getExtension('simple_paginator_extension')->render("player", null, array("container_class" => "pagination", "previousPageText" => "Anterior", "nextPageText" => "Siguiente", "currentClass" => "active", "firstPageText" => "Primera", "lastPageText" => "Última", "firstDisabledClass" => "disabled", "lastDisabledClass" => "disabled", "nextDisabledClass" => "disabled", "previousDisabledClass" => "disabled"));
        // line 78
        echo "
</div>
";
    }

    public function getTemplateName()
    {
        return "PlayerBundle:Player:index.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  153 => 78,  145 => 63,  127 => 57,  120 => 55,  194 => 91,  161 => 74,  146 => 71,  52 => 8,  20 => 1,  180 => 69,  175 => 70,  100 => 38,  76 => 32,  74 => 25,  70 => 28,  83 => 22,  37 => 5,  84 => 15,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 83,  167 => 53,  157 => 64,  152 => 47,  139 => 1,  134 => 61,  124 => 59,  113 => 46,  104 => 45,  96 => 38,  77 => 21,  212 => 77,  198 => 92,  191 => 67,  174 => 62,  170 => 64,  150 => 72,  137 => 62,  129 => 33,  114 => 39,  110 => 64,  65 => 12,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 112,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 60,  128 => 60,  111 => 44,  107 => 63,  61 => 11,  273 => 96,  269 => 94,  254 => 92,  246 => 90,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 59,  131 => 58,  119 => 30,  108 => 39,  102 => 23,  71 => 23,  67 => 22,  63 => 15,  59 => 17,  47 => 12,  28 => 2,  87 => 35,  55 => 16,  98 => 31,  93 => 28,  88 => 16,  78 => 44,  46 => 10,  43 => 6,  40 => 5,  38 => 7,  26 => 6,  44 => 19,  31 => 3,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 67,  142 => 2,  138 => 54,  136 => 16,  123 => 56,  121 => 48,  117 => 47,  115 => 66,  105 => 40,  91 => 36,  69 => 13,  66 => 18,  62 => 16,  49 => 8,  32 => 4,  101 => 32,  94 => 37,  89 => 35,  85 => 49,  79 => 17,  75 => 24,  72 => 24,  68 => 15,  56 => 9,  50 => 10,  41 => 36,  27 => 4,  22 => 4,  35 => 6,  29 => 3,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  209 => 94,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 86,  182 => 66,  176 => 84,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 86,  130 => 39,  125 => 70,  122 => 31,  116 => 54,  112 => 26,  109 => 34,  106 => 33,  103 => 62,  99 => 39,  95 => 28,  92 => 26,  86 => 25,  82 => 18,  80 => 26,  73 => 14,  64 => 19,  60 => 24,  57 => 4,  54 => 3,  51 => 15,  48 => 7,  45 => 8,  42 => 8,  39 => 7,  36 => 29,  33 => 3,  30 => 2,);
    }
}
