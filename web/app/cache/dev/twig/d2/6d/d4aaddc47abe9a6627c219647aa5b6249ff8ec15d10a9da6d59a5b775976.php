<?php

/* CategoryBundle:Category:new.html.twig */
class __TwigTemplate_d26dd4aaddc47abe9a6627c219647aa5b6249ff8ec15d10a9da6d59a5b775976 extends Twig_Template
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

    // line 2
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 3
        echo "  <script src=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/category/js/words.js"), "html", null, true);
        echo "\"></script>
";
    }

    // line 5
    public function block_body($context, array $blocks = array())
    {
        // line 6
        echo "<h1>Crear nueva categor&iacute;a </h1>  
  <ol class=\"breadcrumb\">
    <li><a href=\"";
        // line 8
        echo $this->env->getExtension('routing')->getPath("category");
        echo "\">Volver al listado de categorías</a></li>
  </ol>
       ";
        // line 10
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'form_start');
        echo "
    
    ";
        // line 12
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "name"), 'row');
        echo "
    ";
        // line 13
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "isFixed"), 'row');
        echo "

    ";
        // line 21
        echo "
    <h3>Palabras:</h3>
    ";
        // line 23
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "acceptedWordsBatch"), 'label');
        echo "<br>
    ";
        // line 24
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "acceptedWordsBatch"), 'widget');
        echo "<br><br>
    <a href=\"#\" class=\"add_word_link\" id=\"add_word_link\">Agregar Palabra</a>
    <table>
        <caption></caption>
        <thead>
          <tr>
            <th>Palabra</th>
          </tr>
        </thead>

        <tbody>
            ";
        // line 35
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "acceptedWords"));
        foreach ($context['_seq'] as $context["_key"] => $context["word"]) {
            // line 36
            echo "                ";
            echo $this->getAttribute($this, "prototype", array(0 => (isset($context["word"]) ? $context["word"] : $this->getContext($context, "word"))), "method");
            echo "
            ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['word'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 38
        echo "        </tbody>
    </table>

 <div class=\"col-lg-12\">
  <div class=\"form-group\">
    <br>";
        // line 43
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "submit"), 'row');
        echo "
  </div>
</div>
";
        // line 46
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'rest');
        echo "
";
        // line 47
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'form_end');
        echo "


";
    }

    // line 15
    public function getprototype($_word = null)
    {
        $context = $this->env->mergeGlobals(array(
            "word" => $_word,
        ));

        $blocks = array();

        ob_start();
        try {
            // line 16
            echo "         <tr>
             <td>";
            // line 17
            echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["word"]) ? $context["word"] : $this->getContext($context, "word")), "value"), 'widget');
            echo "</td>
             <td> &nbsp;&nbsp;<a href=\"#\">Eliminar Palabra</a></td>
         </tr>
     ";
        } catch (Exception $e) {
            ob_end_clean();

            throw $e;
        }

        return ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
    }

    public function getTemplateName()
    {
        return "CategoryBundle:Category:new.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  175 => 70,  100 => 38,  76 => 22,  70 => 19,  84 => 15,  81 => 14,  34 => 6,  190 => 60,  185 => 59,  172 => 55,  167 => 53,  152 => 47,  134 => 41,  124 => 38,  113 => 46,  104 => 45,  77 => 21,  212 => 77,  198 => 71,  191 => 67,  174 => 62,  170 => 64,  150 => 5,  137 => 52,  129 => 33,  114 => 39,  110 => 33,  65 => 21,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 54,  140 => 53,  132 => 64,  128 => 16,  107 => 43,  61 => 5,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 53,  119 => 30,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  28 => 2,  87 => 35,  93 => 28,  88 => 16,  78 => 26,  46 => 8,  38 => 7,  26 => 11,  44 => 37,  31 => 3,  201 => 92,  196 => 63,  183 => 65,  171 => 61,  166 => 21,  163 => 20,  158 => 58,  156 => 15,  151 => 63,  142 => 18,  138 => 54,  136 => 16,  121 => 48,  117 => 47,  105 => 40,  91 => 36,  62 => 16,  49 => 8,  94 => 19,  89 => 25,  85 => 25,  75 => 12,  68 => 15,  56 => 12,  27 => 4,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  79 => 21,  72 => 17,  69 => 23,  47 => 12,  40 => 8,  37 => 5,  22 => 2,  246 => 90,  157 => 48,  145 => 46,  139 => 17,  131 => 48,  123 => 47,  120 => 40,  115 => 43,  111 => 44,  108 => 46,  101 => 32,  98 => 31,  96 => 38,  83 => 22,  74 => 25,  66 => 18,  55 => 13,  52 => 21,  50 => 10,  43 => 9,  41 => 36,  35 => 6,  32 => 3,  29 => 2,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 64,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 58,  144 => 44,  141 => 70,  133 => 55,  130 => 39,  125 => 15,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 36,  103 => 32,  99 => 29,  95 => 28,  92 => 37,  86 => 25,  82 => 22,  80 => 19,  73 => 24,  64 => 14,  60 => 13,  57 => 4,  54 => 3,  51 => 10,  48 => 11,  45 => 17,  42 => 6,  39 => 5,  36 => 29,  33 => 4,  30 => 5,);
    }
}
