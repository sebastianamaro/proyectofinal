<?php

/* WebProfilerBundle:Router:panel.html.twig */
class __TwigTemplate_26371d239bbc39830c1fc0654eb7d1909060ae8413aeec97cc333415a9806bf6 extends Twig_Template
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
        echo "<h2>Routing for \"";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["request"]) ? $context["request"] : $this->getContext($context, "request")), "pathinfo"), "html", null, true);
        echo "\"</h2>

<ul>
    <li>
        <strong>Route:&nbsp;</strong>
        ";
        // line 6
        if ($this->getAttribute((isset($context["request"]) ? $context["request"] : $this->getContext($context, "request")), "route")) {
            // line 7
            echo "            ";
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["request"]) ? $context["request"] : $this->getContext($context, "request")), "route"), "html", null, true);
            echo "
        ";
        } else {
            // line 9
            echo "            <em>No matching route</em>
        ";
        }
        // line 11
        echo "    </li>
    <li>
        <strong>Route parameters:&nbsp;</strong>
        ";
        // line 14
        if (twig_length_filter($this->env, $this->getAttribute((isset($context["request"]) ? $context["request"] : $this->getContext($context, "request")), "routeParams"))) {
            // line 15
            echo "            ";
            $this->env->loadTemplate("@WebProfiler/Profiler/table.html.twig")->display(array("data" => $this->getAttribute((isset($context["request"]) ? $context["request"] : $this->getContext($context, "request")), "routeParams"), "class" => "inline"));
            // line 16
            echo "        ";
        } else {
            // line 17
            echo "            <em>No parameters</em>
        ";
        }
        // line 19
        echo "    </li>
    ";
        // line 20
        if ($this->getAttribute((isset($context["router"]) ? $context["router"] : $this->getContext($context, "router")), "redirect")) {
            // line 21
            echo "    <li>
        <strong>Redirecting to:&nbsp;</strong> \"";
            // line 22
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["router"]) ? $context["router"] : $this->getContext($context, "router")), "targetUrl"), "html", null, true);
            echo "\" ";
            if ($this->getAttribute((isset($context["router"]) ? $context["router"] : $this->getContext($context, "router")), "targetRoute")) {
                echo "(route: \"";
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["router"]) ? $context["router"] : $this->getContext($context, "router")), "targetRoute"), "html", null, true);
                echo "\")";
            }
            // line 23
            echo "    <li>
    ";
        }
        // line 25
        echo "    <li>
        <strong>Route matching logs</strong>
        <table class=\"routing inline\">
            <tr>
                <th>Route name</th>
                <th>Pattern</th>
                <th>Log</th>
            </tr>
            ";
        // line 33
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["traces"]) ? $context["traces"] : $this->getContext($context, "traces")));
        foreach ($context['_seq'] as $context["_key"] => $context["trace"]) {
            // line 34
            echo "                <tr class=\"";
            echo (((1 == $this->getAttribute((isset($context["trace"]) ? $context["trace"] : $this->getContext($context, "trace")), "level"))) ? ("almost") : ((((2 == $this->getAttribute((isset($context["trace"]) ? $context["trace"] : $this->getContext($context, "trace")), "level"))) ? ("matches") : (""))));
            echo "\">
                    <td>";
            // line 35
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["trace"]) ? $context["trace"] : $this->getContext($context, "trace")), "name"), "html", null, true);
            echo "</td>
                    <td>";
            // line 36
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["trace"]) ? $context["trace"] : $this->getContext($context, "trace")), "path"), "html", null, true);
            echo "</td>
                    <td>";
            // line 37
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["trace"]) ? $context["trace"] : $this->getContext($context, "trace")), "log"), "html", null, true);
            echo "</td>
                </tr>
            ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['trace'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 40
        echo "        </table>
        <em><small>Note: The above matching is based on the configuration for the current router which might differ from
        the configuration used while routing this request.</small></em>
    </li>
</ul>
";
    }

    public function getTemplateName()
    {
        return "WebProfilerBundle:Router:panel.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  53 => 17,  153 => 78,  127 => 57,  194 => 91,  161 => 74,  146 => 71,  20 => 1,  180 => 69,  175 => 70,  100 => 36,  76 => 32,  70 => 28,  84 => 15,  81 => 14,  34 => 6,  190 => 73,  185 => 59,  172 => 83,  167 => 53,  152 => 47,  134 => 61,  124 => 59,  113 => 40,  104 => 37,  77 => 25,  212 => 77,  198 => 92,  191 => 67,  174 => 62,  170 => 64,  150 => 72,  137 => 62,  129 => 33,  114 => 39,  110 => 64,  65 => 22,  58 => 15,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 112,  220 => 70,  214 => 69,  177 => 56,  169 => 66,  140 => 53,  132 => 60,  128 => 60,  107 => 63,  61 => 11,  273 => 96,  269 => 94,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 16,  143 => 71,  135 => 59,  119 => 30,  102 => 23,  71 => 23,  67 => 22,  63 => 15,  59 => 17,  28 => 6,  87 => 33,  93 => 28,  88 => 16,  78 => 44,  46 => 10,  38 => 7,  26 => 6,  44 => 19,  31 => 3,  201 => 92,  196 => 77,  183 => 65,  171 => 61,  166 => 21,  163 => 65,  158 => 58,  156 => 15,  151 => 67,  142 => 2,  138 => 54,  136 => 16,  121 => 48,  117 => 47,  105 => 40,  91 => 34,  62 => 21,  49 => 8,  94 => 37,  89 => 35,  85 => 49,  75 => 24,  68 => 15,  56 => 9,  27 => 4,  21 => 2,  25 => 3,  24 => 1,  19 => 1,  79 => 17,  72 => 24,  69 => 13,  47 => 15,  40 => 11,  37 => 5,  22 => 4,  246 => 90,  157 => 64,  145 => 63,  139 => 1,  131 => 58,  123 => 56,  120 => 55,  115 => 66,  111 => 44,  108 => 39,  101 => 32,  98 => 31,  96 => 35,  83 => 22,  74 => 25,  66 => 18,  55 => 16,  52 => 8,  50 => 16,  43 => 6,  41 => 36,  35 => 6,  32 => 4,  29 => 3,  209 => 94,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 86,  182 => 66,  176 => 84,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 63,  144 => 44,  141 => 70,  133 => 86,  130 => 39,  125 => 70,  122 => 31,  116 => 54,  112 => 26,  109 => 34,  106 => 36,  103 => 62,  99 => 39,  95 => 28,  92 => 26,  86 => 25,  82 => 18,  80 => 26,  73 => 23,  64 => 19,  60 => 20,  57 => 19,  54 => 3,  51 => 15,  48 => 7,  45 => 14,  42 => 8,  39 => 7,  36 => 9,  33 => 3,  30 => 7,);
    }
}
