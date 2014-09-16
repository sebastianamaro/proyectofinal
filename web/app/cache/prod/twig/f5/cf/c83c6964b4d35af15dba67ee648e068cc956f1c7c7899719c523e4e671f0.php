<?php

/* ::base.html.twig */
class __TwigTemplate_f5cfc83c6964b4d35af15dba67ee648e068cc956f1c7c7899719c523e4e671f0 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'title' => array($this, 'block_title'),
            'stylesheets' => array($this, 'block_stylesheets'),
            'javascriptsHead' => array($this, 'block_javascriptsHead'),
            'body' => array($this, 'block_body'),
            'javascripts' => array($this, 'block_javascripts'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<!DOCTYPE html>
<html>
    <head>
        <meta charset=\"UTF-8\" />
        <title>";
        // line 5
        $this->displayBlock('title', $context, $blocks);
        echo "</title>
        <link rel=\"shortcut icon\" href=\"";
        // line 6
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("favicon.ico"), "html", null, true);
        echo "\" type=\"image/x-icon\">
        <link rel=\"icon\" href=\"";
        // line 7
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("favicon.ico"), "html", null, true);
        echo "\" type=\"image/x-icon\"> 
        <link href=\"";
        // line 8
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/font-awesome-4.2.0/css/font-awesome.css"), "html", null, true);
        echo "\" rel=\"stylesheet\" type=\"text/css\" 
        />
        <!-- Bootstrap core CSS -->
        <link href=\"";
        // line 11
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/css/bootstrap.css"), "html", null, true);
        echo "\" rel=\"stylesheet\" type=\"text/css\" /><link href=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/css/sb-admin.css"), "html", null, true);
        echo "\" rel=\"stylesheet\" type=\"text/css\" />
        <!-- Add custom CSS to GRUPO -->
        <link rel=\"stylesheet\" href=\"";
        // line 13
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/css/jquery-ui.css"), "html", null, true);
        echo "\" type=\"text/css\"         />

        ";
        // line 15
        $this->displayBlock('stylesheets', $context, $blocks);
        // line 16
        echo "        
        <!-- JavaScript -->
         <script src=\"";
        // line 18
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/js/jquery-1.9.1.js"), "html", null, true);
        echo "\"></script>
         <script src=\"";
        // line 19
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/js/jquery-ui.js"), "html", null, true);
        echo "\"></script>
        ";
        // line 20
        $this->displayBlock('javascriptsHead', $context, $blocks);
        // line 22
        echo "    </head>
    <body>

 <div id=\"wrapper\">

      <!-- Sidebar -->
      <nav class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class=\"navbar-header\">
          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-ex1-collapse\">
            <span class=\"sr-only\">Toggle navigation</span>
            <span class=\"icon-bar\"></span>
            <span class=\"icon-bar\"></span>
            <span class=\"icon-bar\"></span>
          </button>
          <a class=\"navbar-brand\" href=\"#\">Control Panel</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class=\"collapse navbar-collapse navbar-ex1-collapse\">
          <ul class=\"nav navbar-nav side-nav\">
            <li>
            <li><a href=\"";
        // line 44
        echo $this->env->getExtension('routing')->getPath("category");
        echo "\"><i class=\"fa fa-folder\"></i> Categor&iacute;as</a></li>
            <li><a href=\"";
        // line 45
        echo $this->env->getExtension('routing')->getPath("player");
        echo "\"><i class=\"fa fa-users\"></i> Jugadores</a></li>
            <li><a href=\"";
        // line 46
        echo $this->env->getExtension('routing')->getPath("statistics");
        echo "\"><i class=\"fa fa-bar-chart\"></i> Estad&iacute;sticas</a></li>
          </ul>

          <ul class=\"nav navbar-nav navbar-right navbar-user\">
            <li class=\"dropdown user-dropdown\">
              <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-user\"></i> 
                Usuario: <b>";
        // line 52
        echo twig_escape_filter($this->env, $this->getAttribute($this->getAttribute((isset($context["app"]) ? $context["app"] : null), "user"), "username"), "html", null, true);
        echo "</b><b class=\"caret\"></b></a>
              <ul class=\"dropdown-menu\">
                <li><a href=\"#\"><i class=\"fa fa-power-off\"></i> Cerrar sesi&oacute;n</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </nav>

      <div id=\"page-wrapper\">
        <div class=\"row\">
          <div class=\"col-lg-12\">
                ";
        // line 64
        $this->displayBlock('body', $context, $blocks);
        echo "              
          </div>
        </div><!-- /.row -->
      </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->
        ";
        // line 70
        $this->displayBlock('javascripts', $context, $blocks);
        // line 71
        echo "        
    </body>
</html>
";
    }

    // line 5
    public function block_title($context, array $blocks = array())
    {
        echo "Tutti Frutti - Portable Fun";
    }

    // line 15
    public function block_stylesheets($context, array $blocks = array())
    {
        // line 16
        echo "        ";
    }

    // line 20
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 21
        echo "        ";
    }

    // line 64
    public function block_body($context, array $blocks = array())
    {
    }

    // line 70
    public function block_javascripts($context, array $blocks = array())
    {
    }

    public function getTemplateName()
    {
        return "::base.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  175 => 70,  100 => 44,  76 => 22,  74 => 20,  70 => 19,  83 => 22,  37 => 5,  84 => 15,  81 => 14,  34 => 6,  190 => 60,  185 => 59,  172 => 55,  167 => 53,  157 => 48,  152 => 47,  139 => 38,  134 => 41,  124 => 38,  113 => 34,  104 => 45,  96 => 28,  77 => 21,  212 => 77,  198 => 71,  191 => 67,  174 => 62,  170 => 64,  150 => 5,  137 => 52,  129 => 33,  114 => 39,  110 => 33,  65 => 14,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 54,  140 => 53,  132 => 64,  128 => 49,  111 => 37,  107 => 36,  61 => 5,  273 => 96,  269 => 94,  254 => 92,  246 => 90,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 53,  131 => 48,  119 => 30,  108 => 46,  102 => 23,  71 => 16,  67 => 15,  63 => 15,  59 => 14,  47 => 12,  28 => 2,  87 => 25,  55 => 13,  98 => 31,  93 => 28,  88 => 16,  78 => 20,  46 => 7,  43 => 9,  40 => 8,  38 => 7,  26 => 11,  44 => 37,  31 => 3,  201 => 92,  196 => 63,  183 => 65,  171 => 61,  166 => 21,  163 => 20,  158 => 58,  156 => 15,  151 => 63,  142 => 59,  138 => 54,  136 => 37,  123 => 47,  121 => 46,  117 => 52,  115 => 43,  105 => 24,  91 => 27,  69 => 18,  66 => 18,  62 => 16,  49 => 8,  32 => 4,  101 => 32,  94 => 19,  89 => 25,  85 => 25,  79 => 21,  75 => 12,  72 => 17,  68 => 15,  56 => 9,  50 => 10,  41 => 36,  27 => 4,  22 => 2,  35 => 6,  29 => 12,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 64,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 58,  144 => 44,  141 => 70,  133 => 55,  130 => 39,  125 => 32,  122 => 31,  116 => 35,  112 => 26,  109 => 34,  106 => 33,  103 => 32,  99 => 29,  95 => 28,  92 => 27,  86 => 25,  82 => 22,  80 => 19,  73 => 19,  64 => 17,  60 => 15,  57 => 4,  54 => 3,  51 => 13,  48 => 11,  45 => 8,  42 => 8,  39 => 30,  36 => 29,  33 => 4,  30 => 5,);
    }
}
