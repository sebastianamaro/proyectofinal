<?php

/* StatisticsBundle:Default:index.html.twig */
class __TwigTemplate_e3b55dd5ed79c773e35a6376af8b0bd8b442efa2e68b4c13354c87a559464afc extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("::base.html.twig");

        $this->blocks = array(
            'stylesheets' => array($this, 'block_stylesheets'),
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
    public function block_stylesheets($context, array $blocks = array())
    {
        // line 3
        echo "    <link href=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/css/plugins/morris.css"), "html", null, true);
        echo "\" rel=\"stylesheet\">
";
    }

    // line 5
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 6
        echo "\t<script src=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/jquery-1.11.0.js"), "html", null, true);
        echo "\"></script>
\t<script src=\"";
        // line 7
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/bootstrap.min.js"), "html", null, true);
        echo "\"></script>
    <script src=\"";
        // line 8
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/morris/raphael.min.js"), "html", null, true);
        echo "\"></script>
    <script src=\"";
        // line 9
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/morris/morris.min.js"), "html", null, true);
        echo "\"></script>
    <!--[if lte IE 8]><script src=\"js/excanvas.min.js\"></script><![endif]-->
    <script src=\"";
        // line 11
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/flot/jquery.flot.js"), "html", null, true);
        echo "\"></script>
    <script src=\"";
        // line 12
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/flot/jquery.flot.tooltip.min.js"), "html", null, true);
        echo "\"></script>
    <script src=\"";
        // line 13
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/flot/jquery.flot.resize.js"), "html", null, true);
        echo "\"></script>
    <script src=\"";
        // line 14
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/sb-admin/js/plugins/flot/jquery.flot.pie.js"), "html", null, true);
        echo "\"></script>
";
    }

    // line 17
    public function block_body($context, array $blocks = array())
    {
        // line 18
        echo "<div class=\"row\">
  <div class=\"col-lg-6\">
   <div class=\"panel panel-primary\">
      <div class=\"panel-heading\">
          <h3 class=\"panel-title\"><i class=\"fa fa-long-arrow-right\"></i> Categorías libres más usadas</h3>
      </div>
      <div class=\"panel-body\">
          <div class=\"text-right\">
              <a href=\"";
        // line 26
        echo $this->env->getExtension('routing')->getPath("category");
        echo "\">Ver todas <i class=\"fa fa-arrow-circle-right\"></i></a>
          </div>
          <div class=\"flot-chart\">
              <div id=\"mostUsedNonFixedCategories\" ></div>
          </div>
      </div>
  </div>
    <div class=\"panel panel-primary\" >
        <div class=\"panel-heading\">
          <h3 class=\"panel-title\"><i class=\"fa fa-long-arrow-right\"></i> Crecimiento de jugadores en el tiempo</h3>
      </div>
      <div class=\"panel-body\">
          <div class=\"text-right\">
              <a href=\"";
        // line 39
        echo $this->env->getExtension('routing')->getPath("player");
        echo "\">Ver todos <i class=\"fa fa-arrow-circle-right\"></i></a>
          </div>
          <div class=\"flot-chart\">
              <div id=\"playersInTime\" ></div>
          </div>
      </div>
    </div>
</div>
        <div class=\"col-lg-6\">
   <div class=\"panel panel-primary\">
      <div class=\"panel-heading\">
            <h3 class=\"panel-title\"><i class=\"fa fa-long-arrow-right\"></i> Uso de categorías según tipo</h3>
        </div>
        <div class=\"panel-body\">
            <div class=\"flot-chart\">
                <div id=\"usedCategoriesPerType\" ></div>
            </div>
            
        </div>
        
  </div>                
";
        // line 60
        ob_start();
        // line 61
        echo "
<script>
var mesPorNumero = function(d){
  var nombreMeses = [ \"Enero\", \"Febrero\", \"Marzo\", \"Abril\", \"Mayo\", \"Junio\",
    \"Julio\", \"Agosto\", \"Septiembre\", \"Octubre\", \"Noviembre\", \"Diciembre\" ];
    return nombreMeses[d.getMonth()];
}
new Morris.Bar({
  element: 'mostUsedNonFixedCategories',
  data: [
    ";
        // line 71
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["mostUsedNonFixedCategories"]) ? $context["mostUsedNonFixedCategories"] : $this->getContext($context, "mostUsedNonFixedCategories")));
        foreach ($context['_seq'] as $context["_key"] => $context["category"]) {
            // line 72
            echo "      { y: '";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "name"), "html", null, true);
            echo "', value: ";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["category"]) ? $context["category"] : $this->getContext($context, "category")), "hits"), "html", null, true);
            echo " },
    ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['category'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 74
        echo "  ],
  xkey: 'y',
  ykeys: ['value'],
  labels: ['usos de la categoría'],
  postUnits: ' Usos'
});
new Morris.Donut({
  element: 'usedCategoriesPerType',
  data: [
  ";
        // line 83
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["usedCategoriesPerType"]) ? $context["usedCategoriesPerType"] : $this->getContext($context, "usedCategoriesPerType")));
        foreach ($context['_seq'] as $context["_key"] => $context["type"]) {
            // line 84
            echo "    { label: '";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["type"]) ? $context["type"] : $this->getContext($context, "type")), "type"), "html", null, true);
            echo "', value: ";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["type"]) ? $context["type"] : $this->getContext($context, "type")), "hits"), "html", null, true);
            echo " },
  ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['type'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 86
        echo "  ]
});
new Morris.Line({
  element: 'playersInTime',
  data: [
  ";
        // line 91
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["playersInTime"]) ? $context["playersInTime"] : $this->getContext($context, "playersInTime")));
        foreach ($context['_seq'] as $context["_key"] => $context["playerTime"]) {
            // line 92
            echo "    { date: '";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["playerTime"]) ? $context["playerTime"] : $this->getContext($context, "playerTime")), "date"), "html", null, true);
            echo "', amount: ";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["playerTime"]) ? $context["playerTime"] : $this->getContext($context, "playerTime")), "amount"), "html", null, true);
            echo " },
  ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['playerTime'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 94
        echo "  ],
  xkey: 'date',
  ykeys: ['amount'],
  labels: ['Cantidad de jugadores'],
  xLabels: 'day',
  dateFormat: function (date) {

    d = new Date(date);
    return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear(); 
  },
  xLabelFormat: function (date) {
    d = new Date(date);
    return d.getDate()+'/'+(d.getMonth()+1)+'/'+d.getFullYear(); 
  },
  postUnits: ' Jugadores'
});
</script>
";
        echo trim(preg_replace('/>\s+</', '><', ob_get_clean()));
        // line 112
        echo "
";
    }

    public function getTemplateName()
    {
        return "StatisticsBundle:Default:index.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  194 => 91,  161 => 74,  146 => 71,  20 => 1,  180 => 69,  175 => 70,  100 => 38,  76 => 32,  70 => 28,  84 => 15,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 83,  167 => 53,  152 => 47,  134 => 61,  124 => 59,  113 => 46,  104 => 45,  77 => 21,  212 => 77,  198 => 92,  191 => 67,  174 => 62,  170 => 64,  150 => 72,  137 => 62,  129 => 33,  114 => 39,  110 => 64,  65 => 12,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 112,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 60,  128 => 60,  107 => 63,  61 => 11,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 53,  119 => 30,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  28 => 2,  87 => 35,  93 => 28,  88 => 16,  78 => 44,  46 => 10,  38 => 7,  26 => 6,  44 => 19,  31 => 3,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 63,  142 => 2,  138 => 54,  136 => 16,  121 => 48,  117 => 47,  105 => 40,  91 => 36,  62 => 16,  49 => 8,  94 => 37,  89 => 25,  85 => 49,  75 => 12,  68 => 15,  56 => 9,  27 => 4,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  79 => 17,  72 => 24,  69 => 13,  47 => 12,  40 => 5,  37 => 5,  22 => 4,  246 => 90,  157 => 64,  145 => 46,  139 => 1,  131 => 74,  123 => 47,  120 => 40,  115 => 66,  111 => 44,  108 => 39,  101 => 32,  98 => 31,  96 => 38,  83 => 22,  74 => 25,  66 => 18,  55 => 13,  52 => 8,  50 => 10,  43 => 6,  41 => 36,  35 => 6,  32 => 4,  29 => 3,  209 => 94,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 86,  182 => 66,  176 => 84,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 86,  130 => 39,  125 => 70,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 36,  103 => 62,  99 => 39,  95 => 28,  92 => 26,  86 => 25,  82 => 18,  80 => 26,  73 => 14,  64 => 19,  60 => 24,  57 => 4,  54 => 3,  51 => 21,  48 => 7,  45 => 17,  42 => 8,  39 => 7,  36 => 29,  33 => 3,  30 => 2,);
    }
}
