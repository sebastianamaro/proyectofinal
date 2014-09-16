<?php

/* CategoryBundle:Category:words.html.twig */
class __TwigTemplate_29871f1b7ffee131ffab403d6ba539de5fd2141d33a924f370efa14291a1da04 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'javascriptsHead' => array($this, 'block_javascriptsHead'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        $this->displayBlock('javascriptsHead', $context, $blocks);
        // line 4
        echo "<b>Palabras reportadas</b>
<div class=\"count\">
    ";
        // line 6
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["reportedWordsPage"]) ? $context["reportedWordsPage"] : $this->getContext($context, "reportedWordsPage"))), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "reportedWords")), "html", null, true);
        echo " palabras reportadas
</div>
<br>
<div class=\"table-responsive\">
  <table class=\"table table-bordered table-hover table-striped tablesorter\">
    <thead>
      <tr>
        <th>Palabra</th>
        <th>Acciones</th>

      </tr>
    </thead>
    <tbody> 
    ";
        // line 19
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["reportedWordsPage"]) ? $context["reportedWordsPage"] : $this->getContext($context, "reportedWordsPage")));
        foreach ($context['_seq'] as $context["_key"] => $context["word"]) {
            // line 20
            echo "            <tr>
                <td>";
            // line 21
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"), "html", null, true);
            echo "</td>
                <td>
                    <a href=\"";
            // line 23
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("reportedword_accept", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"), "word" => $this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"))), "html", null, true);
            echo "\" class=\"btn btn-primary btn-xs\">aceptar</a>
                    <a href=\"";
            // line 24
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("reportedword_reject", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"), "word" => $this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"))), "html", null, true);
            echo "\" class=\"btn btn-danger btn-xs\">rechazar</a>
                </td>
            </tr>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['word'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 28
        echo "    </tbody>
  </table>
</div>
<div class=\"pagination\">
    ";
        // line 32
        echo $this->env->getExtension('simple_paginator_extension')->render((isset($context["path"]) ? $context["path"] : $this->getContext($context, "path")), "reportedWords", array("routeParams" => array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id")), "container_class" => "pagination", "previousPageText" => "Anterior", "nextPageText" => "Siguiente", "currentClass" => "active", "firstPageText" => "Primera", "lastPageText" => "Última", "firstDisabledClass" => "disabled", "lastDisabledClass" => "disabled", "nextDisabledClass" => "disabled", "previousDisabledClass" => "disabled"));
        // line 44
        echo "
</div>
<br>
<b>Palabras aceptadas</b>
<div class=\"count\">
    ";
        // line 49
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["acceptedWordsPage"]) ? $context["acceptedWordsPage"] : $this->getContext($context, "acceptedWordsPage"))), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "acceptedWords")), "html", null, true);
        echo " palabras aceptadas
</div>
<br>
<div class=\"table-responsive\">
  <table class=\"table table-bordered table-hover table-striped tablesorter\">
    <thead>
      <tr>
        <th>Palabra</th>
        <th>Acciones</th>

      </tr>
    </thead>
    <tbody> 
    ";
        // line 62
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["acceptedWordsPage"]) ? $context["acceptedWordsPage"] : $this->getContext($context, "acceptedWordsPage")));
        foreach ($context['_seq'] as $context["_key"] => $context["word"]) {
            // line 63
            echo "            <tr>
                <td>";
            // line 64
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"), "html", null, true);
            echo "</td>
                <td>
                    <a href=\"";
            // line 66
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath("acceptedword_delete", array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id"), "word" => $this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"))), "html", null, true);
            echo "\" class=\"btn btn-danger btn-xs\">eliminar</a>
                </td>
            </tr>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['word'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 70
        echo "    </tbody>
  </table>
</div>
<div class=\"pagination\">
    ";
        // line 74
        echo $this->env->getExtension('simple_paginator_extension')->render((isset($context["path"]) ? $context["path"] : $this->getContext($context, "path")), "acceptedWords", array("routeParams" => array("id" => $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "id")), "container_class" => "pagination", "previousPageText" => "Anterior", "nextPageText" => "Siguiente", "currentClass" => "active", "firstPageText" => "Primera", "lastPageText" => "Última", "firstDisabledClass" => "disabled", "lastDisabledClass" => "disabled", "nextDisabledClass" => "disabled", "previousDisabledClass" => "disabled"));
        // line 86
        echo "
</div>
<br>";
    }

    // line 1
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 2
        echo "  <script src=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/js/agregarLinkPaginadores.js"), "html", null, true);
        echo "\"></script>
";
    }

    public function getTemplateName()
    {
        return "CategoryBundle:Category:words.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  20 => 1,  180 => 69,  175 => 70,  100 => 38,  76 => 32,  70 => 28,  84 => 15,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 55,  167 => 53,  152 => 47,  134 => 41,  124 => 59,  113 => 46,  104 => 45,  77 => 21,  212 => 77,  198 => 88,  191 => 67,  174 => 62,  170 => 64,  150 => 5,  137 => 62,  129 => 33,  114 => 39,  110 => 64,  65 => 21,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 64,  128 => 60,  107 => 63,  61 => 5,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 53,  119 => 30,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  28 => 2,  87 => 35,  93 => 28,  88 => 16,  78 => 44,  46 => 10,  38 => 7,  26 => 6,  44 => 19,  31 => 3,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 63,  142 => 2,  138 => 54,  136 => 16,  121 => 48,  117 => 47,  105 => 40,  91 => 36,  62 => 16,  49 => 8,  94 => 37,  89 => 25,  85 => 49,  75 => 12,  68 => 15,  56 => 23,  27 => 4,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  79 => 21,  72 => 24,  69 => 23,  47 => 12,  40 => 8,  37 => 5,  22 => 4,  246 => 90,  157 => 64,  145 => 46,  139 => 1,  131 => 74,  123 => 47,  120 => 40,  115 => 66,  111 => 44,  108 => 46,  101 => 32,  98 => 31,  96 => 38,  83 => 22,  74 => 25,  66 => 18,  55 => 13,  52 => 21,  50 => 10,  43 => 9,  41 => 36,  35 => 6,  32 => 4,  29 => 3,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 68,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 86,  130 => 39,  125 => 70,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 36,  103 => 62,  99 => 39,  95 => 28,  92 => 37,  86 => 25,  82 => 22,  80 => 26,  73 => 24,  64 => 19,  60 => 24,  57 => 4,  54 => 3,  51 => 21,  48 => 20,  45 => 17,  42 => 8,  39 => 7,  36 => 29,  33 => 4,  30 => 5,);
    }
}
