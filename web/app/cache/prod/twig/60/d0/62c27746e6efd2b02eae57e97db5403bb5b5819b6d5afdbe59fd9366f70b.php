<?php

/* IdeupSimplePaginatorBundle:Paginator:simple-paginator-list-view.html.twig */
class __TwigTemplate_60d062c27746e6efd2b02eae57e97db5403bb5b5819b6d5afdbe59fd9366f70b extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        // line 1
        echo "<ul class=\"";
        echo twig_escape_filter($this->env, (isset($context["container_class"]) ? $context["container_class"] : null), "html", null, true);
        echo "\">
    <!-- FIRST -->
    ";
        // line 3
        if (((isset($context["currentPage"]) ? $context["currentPage"] : null) > 1)) {
            // line 4
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["firstPage"]) ? $context["firstPage"] : null), "paginatorId" => (isset($context["id"]) ? $context["id"] : null)), (isset($context["routeParams"]) ? $context["routeParams"] : null));
            // line 5
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["firstEnabledClass"]) ? $context["firstEnabledClass"] : null), "html", null, true);
            echo "\">
            <a href=\"";
            // line 6
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : null), (isset($context["rParams"]) ? $context["rParams"] : null)), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["firstPageText"]) ? $context["firstPageText"] : null), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 9
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["firstDisabledClass"]) ? $context["firstDisabledClass"] : null), "html", null, true);
            echo "\">
            ";
            // line 10
            echo twig_escape_filter($this->env, (isset($context["firstPageText"]) ? $context["firstPageText"] : null), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 13
        echo "
    <!-- PREVIOUS -->
    ";
        // line 15
        if (((isset($context["currentPage"]) ? $context["currentPage"] : null) > 1)) {
            echo "   
        ";
            // line 16
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["previousPage"]) ? $context["previousPage"] : null), "paginatorId" => (isset($context["id"]) ? $context["id"] : null)), (isset($context["routeParams"]) ? $context["routeParams"] : null));
            // line 17
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["previousEnabledClass"]) ? $context["previousEnabledClass"] : null), "html", null, true);
            echo "\">
            <a href=\"";
            // line 18
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : null), (isset($context["rParams"]) ? $context["rParams"] : null)), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["previousPageText"]) ? $context["previousPageText"] : null), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 21
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["previousDisabledClass"]) ? $context["previousDisabledClass"] : null), "html", null, true);
            echo "\">
            ";
            // line 22
            echo twig_escape_filter($this->env, (isset($context["previousPageText"]) ? $context["previousPageText"] : null), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 25
        echo "
    <!-- NUMBERS -->
    ";
        // line 27
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable(range((isset($context["minPage"]) ? $context["minPage"] : null), (isset($context["maxPage"]) ? $context["maxPage"] : null)));
        foreach ($context['_seq'] as $context["_key"] => $context["page"]) {
            // line 28
            echo "        ";
            if (((isset($context["page"]) ? $context["page"] : null) == (isset($context["currentPage"]) ? $context["currentPage"] : null))) {
                // line 29
                echo "            <li class=\"";
                echo twig_escape_filter($this->env, (isset($context["currentClass"]) ? $context["currentClass"] : null), "html", null, true);
                echo "\">
                ";
                // line 30
                echo twig_escape_filter($this->env, (isset($context["page"]) ? $context["page"] : null), "html", null, true);
                echo "
            </li>
        ";
            } else {
                // line 33
                echo "            ";
                $context["rParams"] = twig_array_merge(array("page" => (isset($context["page"]) ? $context["page"] : null), "paginatorId" => (isset($context["id"]) ? $context["id"] : null)), (isset($context["routeParams"]) ? $context["routeParams"] : null));
                // line 34
                echo "            <li>
                <a href=\"";
                // line 35
                echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : null), (isset($context["rParams"]) ? $context["rParams"] : null)), "html", null, true);
                echo "\">";
                echo twig_escape_filter($this->env, (isset($context["page"]) ? $context["page"] : null), "html", null, true);
                echo "</a>
            </li>
        ";
            }
            // line 38
            echo "    ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['page'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 39
        echo "
    <!-- NEXT -->
    ";
        // line 41
        if (((isset($context["currentPage"]) ? $context["currentPage"] : null) < (isset($context["lastPage"]) ? $context["lastPage"] : null))) {
            // line 42
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["nextPage"]) ? $context["nextPage"] : null), "paginatorId" => (isset($context["id"]) ? $context["id"] : null)), (isset($context["routeParams"]) ? $context["routeParams"] : null));
            // line 43
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["nextEnabledClass"]) ? $context["nextEnabledClass"] : null), "html", null, true);
            echo "\">
            <a href=\"";
            // line 44
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : null), (isset($context["rParams"]) ? $context["rParams"] : null)), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["nextPageText"]) ? $context["nextPageText"] : null), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 47
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["nextDisabledClass"]) ? $context["nextDisabledClass"] : null), "html", null, true);
            echo "\">
            ";
            // line 48
            echo twig_escape_filter($this->env, (isset($context["nextPageText"]) ? $context["nextPageText"] : null), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 51
        echo "
    <!-- LAST -->
    ";
        // line 53
        if (((isset($context["currentPage"]) ? $context["currentPage"] : null) < (isset($context["lastPage"]) ? $context["lastPage"] : null))) {
            // line 54
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["lastPage"]) ? $context["lastPage"] : null), "paginatorId" => (isset($context["id"]) ? $context["id"] : null)), (isset($context["routeParams"]) ? $context["routeParams"] : null));
            // line 55
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["lastEnabledClass"]) ? $context["lastEnabledClass"] : null), "html", null, true);
            echo "\">
            <a href=\"";
            // line 56
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : null), (isset($context["rParams"]) ? $context["rParams"] : null)), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["lastPageText"]) ? $context["lastPageText"] : null), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 59
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["lastDisabledClass"]) ? $context["lastDisabledClass"] : null), "html", null, true);
            echo "\">
            ";
            // line 60
            echo twig_escape_filter($this->env, (isset($context["lastPageText"]) ? $context["lastPageText"] : null), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 63
        echo "</ul>";
    }

    public function getTemplateName()
    {
        return "IdeupSimplePaginatorBundle:Paginator:simple-paginator-list-view.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  190 => 60,  185 => 59,  172 => 55,  167 => 53,  157 => 48,  152 => 47,  139 => 43,  134 => 41,  124 => 38,  113 => 34,  104 => 30,  96 => 28,  77 => 21,  212 => 77,  198 => 71,  191 => 67,  174 => 62,  170 => 61,  150 => 56,  137 => 52,  129 => 47,  114 => 39,  110 => 33,  65 => 14,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 56,  169 => 54,  140 => 53,  132 => 51,  128 => 49,  111 => 37,  107 => 36,  61 => 13,  273 => 96,  269 => 94,  254 => 92,  246 => 90,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 61,  143 => 56,  135 => 53,  131 => 48,  119 => 42,  108 => 36,  102 => 32,  71 => 17,  67 => 15,  63 => 15,  59 => 14,  47 => 7,  28 => 3,  87 => 25,  55 => 10,  98 => 31,  93 => 28,  88 => 25,  78 => 21,  46 => 7,  43 => 9,  40 => 8,  38 => 6,  26 => 6,  44 => 12,  31 => 5,  201 => 92,  196 => 63,  183 => 65,  171 => 61,  166 => 60,  163 => 51,  158 => 58,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 42,  123 => 47,  121 => 46,  117 => 44,  115 => 43,  105 => 40,  91 => 27,  69 => 18,  66 => 15,  62 => 16,  49 => 8,  32 => 4,  101 => 32,  94 => 28,  89 => 20,  85 => 25,  79 => 21,  75 => 17,  72 => 17,  68 => 15,  56 => 9,  50 => 10,  41 => 5,  27 => 4,  22 => 2,  35 => 6,  29 => 3,  21 => 2,  25 => 3,  24 => 2,  19 => 1,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 64,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 58,  144 => 44,  141 => 48,  133 => 55,  130 => 39,  125 => 45,  122 => 44,  116 => 35,  112 => 42,  109 => 34,  106 => 33,  103 => 32,  99 => 29,  95 => 28,  92 => 27,  86 => 25,  82 => 22,  80 => 19,  73 => 19,  64 => 17,  60 => 13,  57 => 11,  54 => 13,  51 => 14,  48 => 10,  45 => 8,  42 => 7,  39 => 6,  36 => 5,  33 => 4,  30 => 5,);
    }
}
