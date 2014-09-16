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
        echo twig_escape_filter($this->env, $this->getAttribute($this->getAttribute((isset($context["app"]) ? $context["app"] : $this->getContext($context, "app")), "user"), "username"), "html", null, true);
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
        return array (  175 => 70,  170 => 64,  166 => 21,  159 => 16,  156 => 15,  150 => 5,  143 => 71,  141 => 70,  132 => 64,  117 => 52,  108 => 46,  104 => 45,  100 => 44,  74 => 20,  70 => 19,  66 => 18,  62 => 16,  55 => 13,  48 => 11,  38 => 7,  34 => 6,  30 => 5,  24 => 1,  198 => 88,  196 => 77,  190 => 73,  180 => 69,  176 => 68,  169 => 66,  163 => 20,  157 => 64,  147 => 63,  137 => 62,  131 => 61,  128 => 60,  124 => 59,  99 => 39,  94 => 37,  80 => 26,  76 => 22,  72 => 24,  64 => 19,  60 => 15,  56 => 17,  46 => 10,  42 => 8,  39 => 7,  32 => 4,  29 => 3,);
    }
}
