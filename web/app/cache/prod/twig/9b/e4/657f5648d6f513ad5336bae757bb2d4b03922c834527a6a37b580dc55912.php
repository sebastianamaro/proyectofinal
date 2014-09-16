<?php

/* LswApiCallerBundle:Collector:api.html.twig */
class __TwigTemplate_9be4657f5648d6f513ad5336bae757bb2d4b03922c834527a6a37b580dc55912 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = $this->env->loadTemplate("WebProfilerBundle:Profiler:layout.html.twig");

        $this->blocks = array(
            'toolbar' => array($this, 'block_toolbar'),
            'menu' => array($this, 'block_menu'),
            'panel' => array($this, 'block_panel'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "WebProfilerBundle:Profiler:layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $this->parent->display($context, array_merge($this->blocks, $blocks));
    }

    // line 3
    public function block_toolbar($context, array $blocks = array())
    {
        // line 4
        echo "    ";
        ob_start();
        // line 5
        echo "        <img width=\"22\" height=\"28\" alt=\"API\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAcCAYAAABlL09dAAAAAXNSR0IArs4c6QAAAAZiS0dEAP8A/wD/oL2nkwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB90DGg4mOhU8H+YAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAACjElEQVRIx+2Wz08TURDHP+/ttlssrZhgWw5gOBhOFCImkJgmHjwgKIkJxIN/AgFjCCZgTI3Gv0Dj3YuGGAU1YOLFRA9E4Aht8KAeEBIbgaVsMW23z4Ow2napP6o3JtnDzNv5ZuY7M28eHMiuiOGhIbm1sXnaNM0Ou1DIVwOmlBKHg8FsbaD2vr7+Zb3O3Nx4aVkZTQhRdaSWZdHkadJkW3tbAIQmhOBffaFIuEtKTfvn/Pp8NZquUG5cAaBrGkLK/Qglb9sopSilUCmldDdQpRThSISenh4CgYArbqFgs7CwwNzbOVdw3c3pSF0dY+NjtEajFVM+29vL7Zu3mJ2dLTsry1MIwYX+flqjUZLJJFdHR8qc9uw+n4+xa+MYXq9DnyuwUgpd1xm4OADAdnqLxcXFMuCf7Yf8fs739ZHL5fYHLhRsjre04PV49+Lfp3jF9lOxGLadrxQxRMJhR8/nc2yn02WwpfZQ6CiFQgUqhIAt03T0Ex0nmZh4BEAmY5FKpQCItrXz+MmU859pmmVdUQQspUYiseTohmEQaWjAsixGR0aIxWK8W17G7/dTX1/vFGx+fgG9ZNBkaUfs7HxlZnq6qKez2Sxra2sArKysOGdCCHL5HM+fPUX3eH7dxxMPHtIajdLY2AhAMBjkejxOeivt9PZe6vfu3CX1OYUsKbLr5H1aXeVGPM7g4CDh3WKGQmFCoTAZyyJjWeTzNlOTk7yYnsHtVtTdBgTg4/sPXBm+XNb4Rc4eHSm13wP+UUiJ1zD++oaTqP+xmBT6d9JVxZT/cD0hpJT6sebmnTPd3SSXltCkVmWcCo/uobOrMylevXntNQwjXuPznRNSZqtdzrZt5xKJxKWDd4oj3wARYAMtKR6xuAAAAABJRU5ErkJggg==\" />
        <span class=\"sf-toolbar-status";
        // line 6
        if ((5 < $this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount"))) {
            echo " sf-toolbar-status-yellow";
        }
        echo "\">";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount"), "html", null, true);
        echo "</span>
        ";
        // line 7
        if (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount") > 0)) {
            // line 8
            echo "            <span class=\"sf-toolbar-info-piece-additional-detail\">in ";
            echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "time") * 1000)), "html", null, true);
            echo " ms</span>
        ";
        }
        // line 10
        echo "        ";
        if (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "returnedErrorCount") > 0)) {
            // line 11
            echo "            <span class=\"sf-toolbar-info-piece-additional sf-toolbar-status sf-toolbar-status-red\"> </span>
        ";
        }
        // line 13
        echo "    ";
        $context["icon"] = ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
        // line 14
        echo "    ";
        ob_start();
        // line 15
        echo "        <div class=\"sf-toolbar-info-piece\">
            <b>API Calls</b>
            <span>";
        // line 17
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount"), "html", null, true);
        echo "</span>
        </div>
        <div class=\"sf-toolbar-info-piece\">
            <b>Total time</b>
            <span>";
        // line 21
        echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "time") * 1000)), "html", null, true);
        echo " ms</span>
        </div>
        <div class=\"sf-toolbar-info-piece\">
            <b>Errors</b>
            <span class=\"sf-toolbar-status sf-toolbar-status-";
        // line 25
        echo ((($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "returnedErrorCount") > 0)) ? ("red") : ("green"));
        echo "\">";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "returnedErrorCount"), "html", null, true);
        echo "</span>
        </div>
    ";
        $context["text"] = ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
        // line 28
        echo "    ";
        $this->env->loadTemplate("WebProfilerBundle:Profiler:toolbar_item.html.twig")->display(array_merge($context, array("link" => (isset($context["profiler_url"]) ? $context["profiler_url"] : null))));
    }

    // line 31
    public function block_menu($context, array $blocks = array())
    {
        // line 32
        echo "<span class=\"label\">
    <span class=\"icon\">
      <img width=\"32\" height=\"33\" alt=\"API\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAhCAYAAAC4JqlRAAAAAXNSR0IArs4c6QAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB90DGRciClbL3V8AAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAC9klEQVRYw+2Xv08TYRjHv8/dvZeUhspw59AfiWy1JEpSwQUHdTCiiUZkN62jQBxJHBzVwRDUKCI6mBD5B5zUxFR+FIOUFZfCIkh7oS2ltL3e60Bai70rV0JoTHiTm+7uuc/7PN/n+7xHgwMD91bj8afFos6ICEe1iIC2E20fqf9Wn65pmogmLckolcRdImoKgIAmr2OApgNI9lqG4Ha7IdgUKgeQTCaRzWYhCMLBATjnyOVyGHk2imAw2FCnpNNp3B8cwko8DkEUD1YCURAwPvEGXV1dICLbFwC4XC68Gn8Nt8cDwzAaBygWi+jrv41ARwc453t2zzm3zFi1p8iyjNEXz5HP71i+Iwb8/oc7+XzNB1T1JB49eVwJqOs6AoEABAK6u89basXv92M7u4WengvgnMPhcIAxGUuxmGkJLTMQCofMRSPtr9vyM0QEzjkuXr4EmTH7JWCSBP9pv2XaGhs6BK/XC6fTaRrPFEBiDIqqHup8ONN5FqVSyR4AEcHpdB6q4ZxqbzftBksA65zWNxbR4n3GGGBSAsm8nQwUCgXIsvw3sCgiEolYZqbcql+/ReBwtNTcX1tbB5m4oul2SnoJyURyj2iICKqqoqVlN7imaQiH7qD36hV8+fyponhFUU0hFxcWIJo4oilAoVjE6upK3VKMjb1EbHERG783MDQ4ULd0mqYhkUjY9wHOOaY+TNV1vbVf60CV9WYyGUtnXIrFUCgU7AMQEaJzc5ifi1ZS+++SmQRZlsEYg8yY6aQsf3B0ZMRyI9R34ybfTKVq6DjncLla8X5yslL36mcSiQR2crnK+PV4PHtGb1mUD4aHMT09Y+mgQj0HS6XSuBsOYyuTqQFUFAVenw9enw8+n69m7huGgXcTbzE7M1vXvi0zUB3I5WpF77XrECV7p3fOge/RKH4uL4MEoa6Y9wUop1PX9YacTxTFfU9DDR3JmMU0Oz4V//8AzfonLItb6DwX5M2A2B1cCmj+x8KEqqihowYgAjZTqe0//VcdSxbB1vAAAAAASUVORK5CYII=\" />
    </span>
    <strong>API CALLS</strong>
    <span class=\"count\">
        <span>";
        // line 38
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount"), "html", null, true);
        echo "</span>
        <span>";
        // line 39
        echo twig_escape_filter($this->env, sprintf("%0.0f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "time") * 1000)), "html", null, true);
        echo " ms</span>
    </span>
</span>
";
    }

    // line 44
    public function block_panel($context, array $blocks = array())
    {
        // line 45
        echo "    <h2>API Calls</h2>

    ";
        // line 47
        if ((!$this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "callcount"))) {
            // line 48
            echo "        <p>
            <em>No calls.</em>
        </p>
    ";
        } else {
            // line 52
            echo "        <ul class=\"alt\">
            ";
            // line 53
            $context['_parent'] = (array) $context;
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["collector"]) ? $context["collector"] : null), "calls"));
            foreach ($context['_seq'] as $context["i"] => $context["call"]) {
                // line 54
                echo "                <li class=\"";
                echo ((((isset($context["i"]) ? $context["i"] : null) % 2 == 1)) ? ("odd") : ("even"));
                echo "\">
                    <div>
                        <strong>Status</strong>: ";
                // line 56
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : null), "status"), "html", null, true);
                echo "<br />
                        <strong>URL</strong>: ";
                // line 57
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : null), "url"), "html", null, true);
                echo "<br />
                        <strong>Type</strong>: ";
                // line 58
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : null), "type"), "html", null, true);
                echo "<br />
                        <strong>Request</strong>: ";
                // line 59
                echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : null), "requestData")), "html", null, true);
                echo " bytes
                          <a href=\"data:text/plain;,";
                // line 60
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "requestData"), true), "html", null, true);
                echo "\">raw</a>
                          <a href=\"data:text/html;,";
                // line 61
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "requestData"), true), "html", null, true);
                echo "\">html</a>
                          <a href=\"data:text/plain;,";
                // line 62
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "requestObject"), true), "html", null, true);
                echo "\">parsed</a>
                          <br />
                        <strong>Response</strong>: ";
                // line 64
                echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : null), "responseData")), "html", null, true);
                echo " bytes
                          <a href=\"data:text/plain;,";
                // line 65
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "responseData"), true), "html", null, true);
                echo "\">raw</a>
                          <a href=\"data:text/html;,";
                // line 66
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "responseData"), true), "html", null, true);
                echo "\">html</a>
                          <a href=\"data:text/plain;,";
                // line 67
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "responseObject"), true), "html", null, true);
                echo "\">parsed</a>
                          <br />
                    </div>
                    <small>
                        <strong>Time</strong>: ";
                // line 71
                echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["call"]) ? $context["call"] : null), "executionMS") * 1000)), "html", null, true);
                echo " ms
                    </small>
                </li>
            ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['i'], $context['call'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 75
            echo "        </ul>
    ";
        }
        // line 77
        echo "
";
    }

    public function getTemplateName()
    {
        return "LswApiCallerBundle:Collector:api.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  212 => 77,  198 => 71,  191 => 67,  174 => 62,  170 => 61,  150 => 56,  137 => 52,  129 => 47,  114 => 39,  110 => 38,  65 => 14,  58 => 11,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  453 => 151,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  407 => 131,  402 => 130,  398 => 129,  393 => 126,  387 => 122,  384 => 121,  381 => 120,  379 => 119,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  360 => 109,  355 => 106,  341 => 105,  337 => 103,  322 => 101,  314 => 99,  312 => 98,  309 => 97,  305 => 95,  298 => 91,  294 => 90,  285 => 89,  283 => 88,  278 => 86,  268 => 85,  264 => 84,  258 => 81,  252 => 80,  247 => 78,  241 => 77,  229 => 73,  220 => 70,  214 => 69,  177 => 65,  169 => 60,  140 => 53,  132 => 51,  128 => 49,  111 => 37,  107 => 36,  61 => 13,  273 => 96,  269 => 94,  254 => 92,  246 => 90,  243 => 88,  240 => 86,  238 => 85,  235 => 74,  230 => 82,  227 => 81,  224 => 71,  221 => 77,  219 => 76,  217 => 75,  208 => 75,  204 => 72,  179 => 64,  159 => 61,  143 => 56,  135 => 53,  131 => 48,  119 => 42,  108 => 36,  102 => 32,  71 => 17,  67 => 15,  63 => 15,  59 => 14,  47 => 7,  28 => 3,  87 => 25,  55 => 10,  98 => 31,  93 => 28,  88 => 6,  78 => 21,  46 => 7,  43 => 6,  40 => 8,  38 => 6,  26 => 6,  44 => 12,  31 => 5,  201 => 92,  196 => 90,  183 => 65,  171 => 61,  166 => 60,  163 => 62,  158 => 58,  156 => 66,  151 => 63,  142 => 59,  138 => 54,  136 => 56,  123 => 47,  121 => 46,  117 => 44,  115 => 43,  105 => 40,  91 => 27,  69 => 25,  66 => 15,  62 => 13,  49 => 8,  32 => 4,  101 => 32,  94 => 28,  89 => 20,  85 => 25,  79 => 21,  75 => 17,  72 => 17,  68 => 15,  56 => 9,  50 => 10,  41 => 5,  27 => 4,  22 => 2,  35 => 5,  29 => 3,  21 => 2,  25 => 4,  24 => 2,  19 => 1,  209 => 82,  203 => 78,  199 => 67,  193 => 73,  189 => 71,  187 => 66,  182 => 66,  176 => 64,  173 => 65,  168 => 72,  164 => 59,  162 => 59,  154 => 57,  149 => 51,  147 => 58,  144 => 54,  141 => 48,  133 => 55,  130 => 41,  125 => 45,  122 => 44,  116 => 41,  112 => 42,  109 => 34,  106 => 33,  103 => 32,  99 => 31,  95 => 28,  92 => 21,  86 => 25,  82 => 22,  80 => 19,  73 => 19,  64 => 14,  60 => 13,  57 => 11,  54 => 10,  51 => 14,  48 => 8,  45 => 8,  42 => 7,  39 => 6,  36 => 5,  33 => 4,  30 => 3,);
    }
}
