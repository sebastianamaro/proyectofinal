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
        if ((5 < $this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount"))) {
            echo " sf-toolbar-status-yellow";
        }
        echo "\">";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount"), "html", null, true);
        echo "</span>
        ";
        // line 7
        if (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount") > 0)) {
            // line 8
            echo "            <span class=\"sf-toolbar-info-piece-additional-detail\">in ";
            echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "time") * 1000)), "html", null, true);
            echo " ms</span>
        ";
        }
        // line 10
        echo "        ";
        if (($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "returnedErrorCount") > 0)) {
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
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount"), "html", null, true);
        echo "</span>
        </div>
        <div class=\"sf-toolbar-info-piece\">
            <b>Total time</b>
            <span>";
        // line 21
        echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "time") * 1000)), "html", null, true);
        echo " ms</span>
        </div>
        <div class=\"sf-toolbar-info-piece\">
            <b>Errors</b>
            <span class=\"sf-toolbar-status sf-toolbar-status-";
        // line 25
        echo ((($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "returnedErrorCount") > 0)) ? ("red") : ("green"));
        echo "\">";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "returnedErrorCount"), "html", null, true);
        echo "</span>
        </div>
    ";
        $context["text"] = ('' === $tmp = ob_get_clean()) ? '' : new Twig_Markup($tmp, $this->env->getCharset());
        // line 28
        echo "    ";
        $this->env->loadTemplate("WebProfilerBundle:Profiler:toolbar_item.html.twig")->display(array_merge($context, array("link" => (isset($context["profiler_url"]) ? $context["profiler_url"] : $this->getContext($context, "profiler_url")))));
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
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount"), "html", null, true);
        echo "</span>
        <span>";
        // line 39
        echo twig_escape_filter($this->env, sprintf("%0.0f", ($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "time") * 1000)), "html", null, true);
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
        if ((!$this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "callcount"))) {
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
            $context['_seq'] = twig_ensure_traversable($this->getAttribute((isset($context["collector"]) ? $context["collector"] : $this->getContext($context, "collector")), "calls"));
            foreach ($context['_seq'] as $context["i"] => $context["call"]) {
                // line 54
                echo "                <li class=\"";
                echo ((((isset($context["i"]) ? $context["i"] : $this->getContext($context, "i")) % 2 == 1)) ? ("odd") : ("even"));
                echo "\">
                    <div>
                        <strong>Status</strong>: ";
                // line 56
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "status"), "html", null, true);
                echo "<br />
                        <strong>URL</strong>: ";
                // line 57
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "url"), "html", null, true);
                echo "<br />
                        <strong>Type</strong>: ";
                // line 58
                echo twig_escape_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "type"), "html", null, true);
                echo "<br />
                        <strong>Request</strong>: ";
                // line 59
                echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "requestData")), "html", null, true);
                echo " bytes
                          <a href=\"data:text/plain;,";
                // line 60
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "requestData"), true), "html", null, true);
                echo "\">raw</a>
                          <a href=\"data:text/html;,";
                // line 61
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "requestData"), true), "html", null, true);
                echo "\">html</a>
                          <a href=\"data:text/plain;,";
                // line 62
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "requestObject"), true), "html", null, true);
                echo "\">parsed</a>
                          <br />
                        <strong>Response</strong>: ";
                // line 64
                echo twig_escape_filter($this->env, twig_length_filter($this->env, $this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "responseData")), "html", null, true);
                echo " bytes
                          <a href=\"data:text/plain;,";
                // line 65
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "responseData"), true), "html", null, true);
                echo "\">raw</a>
                          <a href=\"data:text/html;,";
                // line 66
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "responseData"), true), "html", null, true);
                echo "\">html</a>
                          <a href=\"data:text/plain;,";
                // line 67
                echo twig_escape_filter($this->env, twig_urlencode_filter($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "responseObject"), true), "html", null, true);
                echo "\">parsed</a>
                          <br />
                    </div>
                    <small>
                        <strong>Time</strong>: ";
                // line 71
                echo twig_escape_filter($this->env, sprintf("%0.2f", ($this->getAttribute((isset($context["call"]) ? $context["call"] : $this->getContext($context, "call")), "executionMS") * 1000)), "html", null, true);
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
        return array (  212 => 77,  198 => 71,  137 => 52,  129 => 47,  110 => 38,  65 => 14,  480 => 162,  474 => 161,  469 => 158,  461 => 155,  457 => 153,  444 => 149,  440 => 148,  437 => 147,  435 => 146,  430 => 144,  427 => 143,  423 => 142,  413 => 134,  409 => 132,  402 => 130,  393 => 126,  381 => 120,  379 => 119,  360 => 109,  337 => 103,  322 => 101,  314 => 99,  294 => 90,  268 => 85,  264 => 84,  252 => 80,  247 => 78,  214 => 69,  169 => 60,  140 => 53,  132 => 51,  107 => 36,  273 => 96,  254 => 92,  243 => 88,  240 => 86,  238 => 85,  230 => 82,  224 => 71,  219 => 76,  217 => 75,  179 => 64,  143 => 56,  71 => 17,  209 => 82,  193 => 73,  187 => 66,  154 => 57,  149 => 51,  122 => 44,  112 => 35,  103 => 32,  86 => 25,  57 => 11,  48 => 8,  1077 => 657,  1073 => 656,  1069 => 654,  1064 => 651,  1055 => 648,  1051 => 647,  1048 => 646,  1044 => 645,  1035 => 639,  1026 => 633,  1023 => 632,  1021 => 631,  1018 => 630,  1013 => 627,  1004 => 624,  1000 => 623,  997 => 622,  993 => 621,  984 => 615,  975 => 609,  972 => 608,  970 => 607,  967 => 606,  963 => 604,  959 => 602,  955 => 600,  947 => 597,  941 => 595,  937 => 593,  935 => 592,  930 => 590,  926 => 589,  923 => 588,  919 => 587,  911 => 581,  909 => 580,  905 => 579,  896 => 573,  893 => 572,  891 => 571,  888 => 570,  884 => 568,  880 => 566,  874 => 562,  870 => 560,  864 => 558,  862 => 557,  854 => 552,  848 => 548,  844 => 546,  838 => 544,  836 => 543,  830 => 539,  828 => 538,  824 => 537,  815 => 531,  812 => 530,  810 => 529,  807 => 528,  800 => 523,  796 => 521,  790 => 519,  780 => 513,  774 => 509,  770 => 507,  764 => 505,  762 => 504,  754 => 499,  740 => 491,  737 => 490,  732 => 487,  724 => 484,  718 => 482,  705 => 480,  696 => 476,  692 => 474,  676 => 467,  671 => 465,  668 => 464,  664 => 463,  655 => 457,  646 => 451,  642 => 449,  640 => 448,  636 => 446,  628 => 444,  626 => 443,  622 => 442,  603 => 439,  591 => 436,  587 => 434,  578 => 432,  574 => 431,  565 => 430,  559 => 427,  553 => 425,  551 => 424,  546 => 423,  542 => 421,  536 => 419,  534 => 418,  530 => 417,  527 => 416,  514 => 415,  297 => 200,  280 => 194,  271 => 190,  258 => 81,  251 => 182,  93 => 28,  85 => 25,  77 => 20,  51 => 10,  34 => 5,  31 => 4,  806 => 488,  803 => 487,  792 => 485,  788 => 518,  784 => 482,  771 => 481,  745 => 493,  742 => 492,  723 => 472,  706 => 471,  702 => 479,  698 => 477,  694 => 467,  690 => 466,  686 => 472,  682 => 470,  678 => 468,  675 => 462,  673 => 461,  656 => 460,  645 => 458,  630 => 452,  625 => 450,  621 => 449,  618 => 448,  616 => 440,  602 => 445,  597 => 442,  563 => 429,  545 => 407,  528 => 406,  525 => 405,  523 => 404,  518 => 402,  513 => 400,  202 => 94,  199 => 67,  196 => 92,  182 => 66,  173 => 65,  158 => 58,  136 => 71,  68 => 15,  62 => 13,  28 => 3,  417 => 143,  411 => 140,  407 => 131,  405 => 137,  398 => 129,  395 => 135,  388 => 134,  384 => 121,  382 => 131,  377 => 129,  374 => 116,  368 => 112,  365 => 111,  362 => 110,  359 => 123,  356 => 122,  350 => 120,  347 => 119,  341 => 105,  333 => 115,  324 => 112,  313 => 110,  308 => 109,  305 => 95,  285 => 89,  249 => 181,  237 => 91,  234 => 90,  221 => 77,  201 => 74,  186 => 72,  183 => 65,  180 => 70,  177 => 65,  161 => 58,  159 => 61,  135 => 53,  133 => 42,  128 => 49,  117 => 37,  114 => 39,  95 => 28,  78 => 21,  75 => 18,  58 => 11,  44 => 9,  204 => 72,  188 => 90,  174 => 62,  171 => 61,  167 => 71,  138 => 54,  125 => 45,  121 => 50,  118 => 49,  104 => 31,  87 => 25,  49 => 8,  46 => 7,  27 => 3,  91 => 27,  88 => 24,  63 => 15,  389 => 160,  386 => 159,  378 => 157,  371 => 127,  367 => 155,  363 => 153,  358 => 151,  353 => 121,  345 => 147,  343 => 146,  340 => 145,  334 => 141,  331 => 140,  328 => 113,  326 => 138,  321 => 135,  309 => 97,  307 => 128,  302 => 125,  296 => 121,  293 => 198,  290 => 119,  288 => 118,  283 => 88,  281 => 98,  276 => 193,  274 => 96,  269 => 94,  265 => 105,  259 => 103,  255 => 101,  253 => 100,  235 => 74,  232 => 89,  227 => 81,  222 => 83,  210 => 77,  208 => 75,  189 => 71,  184 => 63,  175 => 58,  170 => 61,  166 => 60,  163 => 62,  155 => 55,  152 => 54,  144 => 54,  127 => 35,  109 => 34,  94 => 28,  82 => 22,  76 => 34,  61 => 13,  39 => 6,  36 => 5,  79 => 21,  72 => 17,  69 => 16,  54 => 10,  47 => 7,  42 => 7,  40 => 11,  37 => 10,  22 => 1,  164 => 59,  157 => 56,  145 => 74,  139 => 45,  131 => 48,  120 => 38,  115 => 39,  111 => 37,  108 => 36,  106 => 33,  101 => 32,  98 => 31,  92 => 27,  83 => 33,  80 => 21,  74 => 14,  66 => 11,  60 => 13,  55 => 10,  52 => 12,  50 => 10,  41 => 8,  32 => 4,  29 => 3,  462 => 202,  453 => 151,  449 => 198,  446 => 197,  441 => 196,  439 => 195,  431 => 189,  429 => 188,  422 => 184,  415 => 180,  408 => 176,  401 => 172,  394 => 168,  387 => 122,  380 => 158,  373 => 156,  361 => 152,  355 => 106,  351 => 141,  348 => 140,  342 => 137,  338 => 116,  335 => 134,  329 => 131,  325 => 129,  323 => 128,  320 => 127,  315 => 111,  312 => 98,  303 => 122,  300 => 121,  298 => 91,  289 => 196,  286 => 112,  278 => 86,  275 => 105,  270 => 102,  267 => 101,  262 => 188,  256 => 96,  248 => 97,  246 => 90,  241 => 77,  233 => 87,  229 => 73,  226 => 84,  220 => 70,  216 => 79,  213 => 78,  207 => 76,  203 => 78,  200 => 72,  197 => 69,  194 => 68,  191 => 67,  185 => 75,  181 => 65,  178 => 59,  176 => 64,  172 => 68,  168 => 62,  165 => 83,  162 => 59,  156 => 58,  153 => 77,  150 => 56,  147 => 58,  141 => 48,  134 => 54,  130 => 41,  123 => 61,  119 => 42,  116 => 41,  113 => 48,  105 => 25,  102 => 32,  99 => 31,  96 => 37,  90 => 26,  84 => 40,  81 => 23,  73 => 19,  70 => 15,  67 => 15,  64 => 14,  59 => 14,  53 => 12,  45 => 8,  43 => 12,  38 => 6,  35 => 5,  33 => 4,  30 => 3,);
    }
}
