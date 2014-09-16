<?php

/* PlayerBundle:Player:index.html.twig */
class __TwigTemplate_ec678aec37a541567007856808458dd240d475590d07116a96f615e93f1013b2 extends Twig_Template
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

    // line 3
    public function block_javascriptsHead($context, array $blocks = array())
    {
        // line 4
        echo "  <script src=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('assets')->getAssetUrl("bundles/utils/js/agregarLinkPaginadores.js"), "html", null, true);
        echo "\"></script>
";
    }

    // line 7
    public function block_body($context, array $blocks = array())
    {
        // line 8
        echo "<h1>Jugadores</h1>  

<form method=\"get\" action=\".\">
<div class=\"table-responsive\">
    <table class=\"table\">
        <thead>
            <tr>
                <th><b>";
        // line 15
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "name"), 'label');
        echo "</b></th>
                <th><b>";
        // line 16
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "email"), 'label');
        echo "</b></th>
                <th><b>";
        // line 17
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "fbId"), 'label');
        echo "</b></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>";
        // line 22
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "name"), 'widget');
        echo "</td> 
                <td>";
        // line 23
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "email"), 'widget');
        echo "</td> 
                <td>";
        // line 24
        echo $this->env->getExtension('form')->renderer->searchAndRenderBlock($this->getAttribute((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), "fbId"), 'widget');
        echo "</td> 
            </tr>
        </tbody>                        
            <tr>
                <td></td>
                <td align='center'><input type=\"submit\" class=\"btn btn-primary\" name=\"submit-filter\" value=\"Buscar\" /></td>
                <td align='center'> <input type=\"submit\" class=\"btn btn-primary\" name=\"clear-filter\" value=\"Blanquear\" /></td>
                <td></td>                        
            </tr>       
    </table>
</div>
";
        // line 35
        echo         $this->env->getExtension('form')->renderer->renderBlock((isset($context["form"]) ? $context["form"] : $this->getContext($context, "form")), 'form_end');
        echo "
<center><ol class=\"breadcrumb\">
    <li>";
        // line 37
        echo twig_escape_filter($this->env, twig_length_filter($this->env, (isset($context["players"]) ? $context["players"] : $this->getContext($context, "players"))), "html", null, true);
        echo " de ";
        echo twig_escape_filter($this->env, (isset($context["totalPlayers"]) ? $context["totalPlayers"] : $this->getContext($context, "totalPlayers")), "html", null, true);
        echo " jugadores</li>
</ol> </center>

<br>
<div class=\"table-responsive\">
  <table class=\"table table-bordered table-hover table-striped tablesorter\">
    <thead>
      <tr>

        <th>Nombre</th>
        <th>Email</th>
        <th>Id. Facebook</th>
        <th>Fecha de alta</th>
        
      </tr>
    </thead>
    <tbody> 
    ";
        // line 54
        $context['_parent'] = (array) $context;
        $context['_seq'] = twig_ensure_traversable((isset($context["players"]) ? $context["players"] : $this->getContext($context, "players")));
        foreach ($context['_seq'] as $context["_key"] => $context["player"]) {
            // line 55
            echo "            <tr>
                <td>";
            // line 56
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : $this->getContext($context, "player")), "name"), "html", null, true);
            echo "</td>
                <td>";
            // line 57
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : $this->getContext($context, "player")), "email"), "html", null, true);
            echo "</td>
                <td>";
            // line 58
            echo twig_escape_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : $this->getContext($context, "player")), "fbId"), "html", null, true);
            echo "</td>
                <td>";
            // line 59
            echo twig_escape_filter($this->env, twig_date_format_filter($this->env, $this->getAttribute((isset($context["player"]) ? $context["player"] : $this->getContext($context, "player")), "dateInserted"), "d/m/Y"), "html", null, true);
            echo "</td>
                
            </tr>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_iterated'], $context['_key'], $context['player'], $context['_parent'], $context['loop']);
        $context = array_intersect_key($context, $_parent) + $_parent;
        // line 63
        echo "    </tbody>
  </table>
</div>
<div class=\"pagination\">
    ";
        // line 67
        echo $this->env->getExtension('simple_paginator_extension')->render("player", null, array("container_class" => "pagination", "previousPageText" => "Anterior", "nextPageText" => "Siguiente", "currentClass" => "active", "firstPageText" => "Primera", "lastPageText" => "Última", "firstDisabledClass" => "disabled", "lastDisabledClass" => "disabled", "nextDisabledClass" => "disabled", "previousDisabledClass" => "disabled"));
        // line 78
        echo "
</div>
";
    }

    public function getTemplateName()
    {
        return "PlayerBundle:Player:index.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  153 => 78,  151 => 67,  145 => 63,  135 => 59,  131 => 58,  127 => 57,  123 => 56,  120 => 55,  116 => 54,  94 => 37,  89 => 35,  75 => 24,  71 => 23,  67 => 22,  59 => 17,  55 => 16,  51 => 15,  42 => 8,  39 => 7,  32 => 4,  29 => 3,);
    }
}
