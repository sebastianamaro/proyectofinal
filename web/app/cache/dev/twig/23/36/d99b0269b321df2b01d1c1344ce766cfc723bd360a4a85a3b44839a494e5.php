<?php

/* WebProfilerBundle:Profiler:info.html.twig */
class __TwigTemplate_2336d99b0269b321df2b01d1c1344ce766cfc723bd360a4a85a3b44839a494e5 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("@WebProfiler/Profiler/base.html.twig");

        $this->blocks = array(
            'body' => array($this, 'block_body'),
            'panel' => array($this, 'block_panel'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "@WebProfiler/Profiler/base.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_body($context, array $blocks = array())
    {
        // line 4
        echo "    <div id=\"content\">
        ";
        // line 5
        $this->env->loadTemplate("@WebProfiler/Profiler/header.html.twig")->display(array());
        // line 6
        echo "
        <div id=\"main\">
            <div class=\"clear-fix\">
                <div id=\"collector-wrapper\">
                    <div id=\"collector-content\">
                        ";
        // line 11
        $this->displayBlock('panel', $context, $blocks);
        // line 34
        echo "                    </div>
                </div>
                <div id=\"navigation\">
                    ";
        // line 37
        echo $this->env->getExtension('http_kernel')->renderFragment($this->env->getExtension('routing')->getPath("_profiler_search_bar"));
        echo "
                    ";
        // line 38
        $this->env->loadTemplate("@WebProfiler/Profiler/admin.html.twig")->display(array("token" => ""));
        // line 39
        echo "                </div>
            </div>
        </div>
    </div>
";
    }

    // line 11
    public function block_panel($context, array $blocks = array())
    {
        // line 12
        echo "                            ";
        if (((isset($context["about"]) ? $context["about"] : $this->getContext($context, "about")) == "purge")) {
            // line 13
            echo "                                <h2>The profiler database was purged successfully</h2>
                                <p>
                                    <em>Now you need to browse some pages with the Symfony Profiler enabled to collect data.</em>
                                </p>
                            ";
        } elseif (((isset($context["about"]) ? $context["about"] : $this->getContext($context, "about")) == "upload_error")) {
            // line 18
            echo "                                <h2>A problem occurred when uploading the data</h2>
                                <p>
                                    <em>No file given or the file was not uploaded successfully.</em>
                                </p>
                            ";
        } elseif (((isset($context["about"]) ? $context["about"] : $this->getContext($context, "about")) == "already_exists")) {
            // line 23
            echo "                                <h2>A problem occurred when uploading the data</h2>
                                <p>
                                    <em>The token already exists in the database.</em>
                                </p>
                            ";
        } elseif (((isset($context["about"]) ? $context["about"] : $this->getContext($context, "about")) == "no_token")) {
            // line 28
            echo "                                <h2>Token not found</h2>
                                <p>
                                    <em>Token \"";
            // line 30
            echo twig_escape_filter($this->env, (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")), "html", null, true);
            echo "\" was not found in the database.</em>
                                </p>
                            ";
        }
        // line 33
        echo "                        ";
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Profiler:info.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  356 => 330,  354 => 329,  351 => 328,  307 => 287,  90 => 37,  53 => 15,  153 => 78,  127 => 57,  194 => 91,  161 => 74,  146 => 71,  20 => 1,  180 => 69,  175 => 70,  100 => 36,  76 => 28,  70 => 26,  84 => 27,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 83,  167 => 53,  152 => 47,  134 => 61,  124 => 59,  113 => 40,  104 => 37,  77 => 25,  212 => 77,  198 => 92,  191 => 67,  174 => 62,  170 => 64,  150 => 72,  137 => 62,  129 => 33,  114 => 39,  110 => 64,  65 => 11,  58 => 18,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 340,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 288,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 112,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 60,  128 => 60,  107 => 63,  61 => 23,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 59,  119 => 30,  102 => 33,  71 => 13,  67 => 22,  63 => 21,  59 => 22,  28 => 3,  87 => 32,  93 => 38,  88 => 16,  78 => 18,  46 => 34,  38 => 6,  26 => 3,  44 => 11,  31 => 4,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 67,  142 => 2,  138 => 54,  136 => 16,  121 => 48,  117 => 47,  105 => 40,  91 => 35,  62 => 24,  49 => 8,  94 => 37,  89 => 30,  85 => 23,  75 => 28,  68 => 12,  56 => 9,  27 => 4,  21 => 2,  25 => 3,  24 => 2,  19 => 1,  79 => 29,  72 => 27,  69 => 26,  47 => 15,  40 => 8,  37 => 6,  22 => 2,  246 => 90,  157 => 64,  145 => 63,  139 => 1,  131 => 58,  123 => 56,  120 => 55,  115 => 66,  111 => 44,  108 => 47,  101 => 43,  98 => 34,  96 => 30,  83 => 30,  74 => 25,  66 => 25,  55 => 38,  52 => 8,  50 => 15,  43 => 6,  41 => 36,  35 => 5,  32 => 4,  29 => 3,  209 => 94,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 86,  182 => 66,  176 => 84,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 86,  130 => 39,  125 => 70,  122 => 31,  116 => 54,  112 => 26,  109 => 34,  106 => 36,  103 => 62,  99 => 39,  95 => 39,  92 => 28,  86 => 25,  82 => 18,  80 => 29,  73 => 23,  64 => 24,  60 => 20,  57 => 39,  54 => 19,  51 => 37,  48 => 7,  45 => 14,  42 => 11,  39 => 10,  36 => 8,  33 => 6,  30 => 5,);
    }
}
