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
        echo twig_escape_filter($this->env, (isset($context["container_class"]) ? $context["container_class"] : $this->getContext($context, "container_class")), "html", null, true);
        echo "\">
    <!-- FIRST -->
    ";
        // line 3
        if (((isset($context["currentPage"]) ? $context["currentPage"] : $this->getContext($context, "currentPage")) > 1)) {
            // line 4
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["firstPage"]) ? $context["firstPage"] : $this->getContext($context, "firstPage")), "paginatorId" => (isset($context["id"]) ? $context["id"] : $this->getContext($context, "id"))), (isset($context["routeParams"]) ? $context["routeParams"] : $this->getContext($context, "routeParams")));
            // line 5
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["firstEnabledClass"]) ? $context["firstEnabledClass"] : $this->getContext($context, "firstEnabledClass")), "html", null, true);
            echo "\">
            <a href=\"";
            // line 6
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : $this->getContext($context, "route")), (isset($context["rParams"]) ? $context["rParams"] : $this->getContext($context, "rParams"))), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["firstPageText"]) ? $context["firstPageText"] : $this->getContext($context, "firstPageText")), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 9
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["firstDisabledClass"]) ? $context["firstDisabledClass"] : $this->getContext($context, "firstDisabledClass")), "html", null, true);
            echo "\">
            ";
            // line 10
            echo twig_escape_filter($this->env, (isset($context["firstPageText"]) ? $context["firstPageText"] : $this->getContext($context, "firstPageText")), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 13
        echo "
    <!-- PREVIOUS -->
    ";
        // line 15
        if (((isset($context["currentPage"]) ? $context["currentPage"] : $this->getContext($context, "currentPage")) > 1)) {
            echo "   
        ";
            // line 16
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["previousPage"]) ? $context["previousPage"] : $this->getContext($context, "previousPage")), "paginatorId" => (isset($context["id"]) ? $context["id"] : $this->getContext($context, "id"))), (isset($context["routeParams"]) ? $context["routeParams"] : $this->getContext($context, "routeParams")));
            // line 17
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["previousEnabledClass"]) ? $context["previousEnabledClass"] : $this->getContext($context, "previousEnabledClass")), "html", null, true);
            echo "\">
            <a href=\"";
            // line 18
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : $this->getContext($context, "route")), (isset($context["rParams"]) ? $context["rParams"] : $this->getContext($context, "rParams"))), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["previousPageText"]) ? $context["previousPageText"] : $this->getContext($context, "previousPageText")), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 21
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["previousDisabledClass"]) ? $context["previousDisabledClass"] : $this->getContext($context, "previousDisabledClass")), "html", null, true);
            echo "\">
            ";
            // line 22
            echo twig_escape_filter($this->env, (isset($context["previousPageText"]) ? $context["previousPageText"] : $this->getContext($context, "previousPageText")), "html", null, true);
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
        $context['_seq'] = twig_ensure_traversable(range((isset($context["minPage"]) ? $context["minPage"] : $this->getContext($context, "minPage")), (isset($context["maxPage"]) ? $context["maxPage"] : $this->getContext($context, "maxPage"))));
        foreach ($context['_seq'] as $context["_key"] => $context["page"]) {
            // line 28
            echo "        ";
            if (((isset($context["page"]) ? $context["page"] : $this->getContext($context, "page")) == (isset($context["currentPage"]) ? $context["currentPage"] : $this->getContext($context, "currentPage")))) {
                // line 29
                echo "            <li class=\"";
                echo twig_escape_filter($this->env, (isset($context["currentClass"]) ? $context["currentClass"] : $this->getContext($context, "currentClass")), "html", null, true);
                echo "\">
                ";
                // line 30
                echo twig_escape_filter($this->env, (isset($context["page"]) ? $context["page"] : $this->getContext($context, "page")), "html", null, true);
                echo "
            </li>
        ";
            } else {
                // line 33
                echo "            ";
                $context["rParams"] = twig_array_merge(array("page" => (isset($context["page"]) ? $context["page"] : $this->getContext($context, "page")), "paginatorId" => (isset($context["id"]) ? $context["id"] : $this->getContext($context, "id"))), (isset($context["routeParams"]) ? $context["routeParams"] : $this->getContext($context, "routeParams")));
                // line 34
                echo "            <li>
                <a href=\"";
                // line 35
                echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : $this->getContext($context, "route")), (isset($context["rParams"]) ? $context["rParams"] : $this->getContext($context, "rParams"))), "html", null, true);
                echo "\">";
                echo twig_escape_filter($this->env, (isset($context["page"]) ? $context["page"] : $this->getContext($context, "page")), "html", null, true);
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
        if (((isset($context["currentPage"]) ? $context["currentPage"] : $this->getContext($context, "currentPage")) < (isset($context["lastPage"]) ? $context["lastPage"] : $this->getContext($context, "lastPage")))) {
            // line 42
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["nextPage"]) ? $context["nextPage"] : $this->getContext($context, "nextPage")), "paginatorId" => (isset($context["id"]) ? $context["id"] : $this->getContext($context, "id"))), (isset($context["routeParams"]) ? $context["routeParams"] : $this->getContext($context, "routeParams")));
            // line 43
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["nextEnabledClass"]) ? $context["nextEnabledClass"] : $this->getContext($context, "nextEnabledClass")), "html", null, true);
            echo "\">
            <a href=\"";
            // line 44
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : $this->getContext($context, "route")), (isset($context["rParams"]) ? $context["rParams"] : $this->getContext($context, "rParams"))), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["nextPageText"]) ? $context["nextPageText"] : $this->getContext($context, "nextPageText")), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 47
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["nextDisabledClass"]) ? $context["nextDisabledClass"] : $this->getContext($context, "nextDisabledClass")), "html", null, true);
            echo "\">
            ";
            // line 48
            echo twig_escape_filter($this->env, (isset($context["nextPageText"]) ? $context["nextPageText"] : $this->getContext($context, "nextPageText")), "html", null, true);
            echo "
        </li>
    ";
        }
        // line 51
        echo "
    <!-- LAST -->
    ";
        // line 53
        if (((isset($context["currentPage"]) ? $context["currentPage"] : $this->getContext($context, "currentPage")) < (isset($context["lastPage"]) ? $context["lastPage"] : $this->getContext($context, "lastPage")))) {
            // line 54
            echo "        ";
            $context["rParams"] = twig_array_merge(array("page" => (isset($context["lastPage"]) ? $context["lastPage"] : $this->getContext($context, "lastPage")), "paginatorId" => (isset($context["id"]) ? $context["id"] : $this->getContext($context, "id"))), (isset($context["routeParams"]) ? $context["routeParams"] : $this->getContext($context, "routeParams")));
            // line 55
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["lastEnabledClass"]) ? $context["lastEnabledClass"] : $this->getContext($context, "lastEnabledClass")), "html", null, true);
            echo "\">
            <a href=\"";
            // line 56
            echo twig_escape_filter($this->env, $this->env->getExtension('routing')->getPath((isset($context["route"]) ? $context["route"] : $this->getContext($context, "route")), (isset($context["rParams"]) ? $context["rParams"] : $this->getContext($context, "rParams"))), "html", null, true);
            echo "\">";
            echo twig_escape_filter($this->env, (isset($context["lastPageText"]) ? $context["lastPageText"] : $this->getContext($context, "lastPageText")), "html", null, true);
            echo "</a>
        </li>
    ";
        } else {
            // line 59
            echo "        <li class=\"";
            echo twig_escape_filter($this->env, (isset($context["lastDisabledClass"]) ? $context["lastDisabledClass"] : $this->getContext($context, "lastDisabledClass")), "html", null, true);
            echo "\">
            ";
            // line 60
            echo twig_escape_filter($this->env, (isset($context["lastPageText"]) ? $context["lastPageText"] : $this->getContext($context, "lastPageText")), "html", null, true);
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
        return array (  185 => 59,  177 => 56,  172 => 55,  167 => 53,  152 => 47,  130 => 39,  113 => 34,  110 => 33,  92 => 27,  88 => 25,  82 => 22,  77 => 21,  58 => 15,  54 => 13,  43 => 9,  35 => 6,  27 => 4,  25 => 3,  19 => 1,  1080 => 340,  1074 => 338,  1068 => 336,  1066 => 335,  1064 => 334,  1060 => 333,  1051 => 332,  1048 => 331,  1036 => 326,  1030 => 324,  1024 => 322,  1022 => 321,  1020 => 320,  1016 => 319,  1010 => 318,  1007 => 317,  995 => 312,  989 => 310,  983 => 308,  981 => 307,  979 => 306,  975 => 305,  971 => 304,  967 => 303,  963 => 302,  957 => 301,  954 => 300,  946 => 296,  942 => 295,  939 => 294,  930 => 287,  928 => 286,  924 => 285,  921 => 284,  916 => 280,  908 => 278,  904 => 277,  902 => 276,  900 => 275,  897 => 274,  891 => 271,  888 => 270,  884 => 267,  881 => 265,  879 => 264,  876 => 263,  869 => 259,  867 => 258,  843 => 257,  840 => 255,  837 => 253,  835 => 252,  833 => 251,  830 => 250,  826 => 247,  824 => 246,  822 => 245,  819 => 244,  815 => 239,  812 => 238,  808 => 235,  806 => 234,  804 => 233,  801 => 232,  797 => 229,  795 => 228,  793 => 227,  791 => 226,  789 => 225,  786 => 224,  782 => 221,  779 => 216,  774 => 212,  754 => 208,  751 => 206,  748 => 205,  745 => 203,  742 => 202,  739 => 200,  737 => 199,  735 => 198,  732 => 197,  728 => 192,  726 => 191,  723 => 190,  719 => 187,  717 => 186,  714 => 185,  704 => 182,  701 => 180,  699 => 179,  696 => 178,  692 => 175,  690 => 174,  687 => 173,  683 => 170,  681 => 169,  678 => 168,  673 => 165,  671 => 164,  668 => 163,  663 => 160,  661 => 159,  658 => 158,  654 => 155,  652 => 154,  649 => 153,  645 => 150,  643 => 149,  640 => 148,  636 => 145,  633 => 144,  629 => 141,  627 => 140,  624 => 139,  620 => 136,  617 => 135,  614 => 133,  609 => 129,  599 => 128,  594 => 127,  592 => 126,  589 => 124,  587 => 123,  584 => 122,  579 => 118,  577 => 116,  576 => 115,  575 => 114,  574 => 113,  570 => 112,  567 => 110,  565 => 109,  562 => 108,  556 => 104,  554 => 103,  552 => 102,  550 => 101,  548 => 100,  544 => 99,  541 => 97,  539 => 96,  536 => 95,  522 => 92,  519 => 91,  505 => 88,  502 => 87,  477 => 82,  474 => 80,  472 => 79,  470 => 78,  465 => 77,  463 => 76,  446 => 75,  443 => 74,  439 => 71,  437 => 70,  435 => 69,  429 => 66,  427 => 65,  425 => 64,  423 => 63,  421 => 62,  412 => 60,  410 => 59,  402 => 58,  399 => 56,  397 => 55,  394 => 54,  389 => 51,  383 => 49,  381 => 48,  377 => 47,  373 => 46,  370 => 45,  365 => 41,  362 => 39,  360 => 38,  357 => 37,  349 => 34,  346 => 33,  342 => 30,  339 => 28,  337 => 27,  334 => 26,  330 => 23,  328 => 22,  326 => 21,  323 => 19,  321 => 18,  317 => 17,  314 => 16,  300 => 13,  298 => 12,  295 => 11,  290 => 7,  287 => 5,  285 => 4,  282 => 3,  278 => 331,  275 => 330,  273 => 317,  270 => 316,  268 => 300,  265 => 299,  263 => 294,  260 => 293,  257 => 291,  255 => 284,  252 => 283,  250 => 274,  247 => 273,  245 => 270,  242 => 269,  240 => 263,  237 => 262,  235 => 250,  232 => 249,  230 => 244,  227 => 243,  224 => 241,  222 => 238,  219 => 237,  217 => 232,  214 => 231,  212 => 224,  209 => 223,  207 => 216,  204 => 215,  201 => 213,  199 => 212,  194 => 197,  191 => 196,  188 => 194,  186 => 190,  183 => 189,  181 => 185,  178 => 184,  173 => 177,  171 => 173,  164 => 163,  161 => 162,  154 => 153,  151 => 152,  149 => 148,  146 => 147,  144 => 44,  139 => 43,  136 => 42,  134 => 41,  129 => 122,  126 => 121,  121 => 107,  119 => 95,  116 => 35,  114 => 91,  111 => 90,  109 => 87,  106 => 86,  101 => 73,  96 => 28,  91 => 44,  89 => 37,  86 => 36,  84 => 33,  81 => 32,  79 => 26,  71 => 15,  69 => 18,  61 => 2,  175 => 70,  170 => 64,  166 => 167,  159 => 158,  156 => 157,  150 => 5,  143 => 71,  141 => 143,  132 => 64,  117 => 52,  108 => 46,  104 => 30,  100 => 44,  74 => 16,  70 => 19,  66 => 10,  62 => 16,  55 => 13,  48 => 10,  38 => 7,  34 => 6,  30 => 5,  24 => 1,  198 => 88,  196 => 63,  190 => 60,  180 => 69,  176 => 178,  169 => 54,  163 => 51,  157 => 48,  147 => 63,  137 => 62,  131 => 132,  128 => 60,  124 => 38,  99 => 29,  94 => 45,  80 => 26,  76 => 25,  72 => 24,  64 => 17,  60 => 15,  56 => 17,  46 => 10,  42 => 8,  39 => 7,  32 => 4,  29 => 3,);
    }
}
