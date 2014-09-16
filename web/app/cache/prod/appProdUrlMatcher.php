<?php

use Symfony\Component\Routing\Exception\MethodNotAllowedException;
use Symfony\Component\Routing\Exception\ResourceNotFoundException;
use Symfony\Component\Routing\RequestContext;

/**
 * appProdUrlMatcher
 *
 * This class has been auto-generated
 * by the Symfony Routing Component.
 */
class appProdUrlMatcher extends Symfony\Bundle\FrameworkBundle\Routing\RedirectableUrlMatcher
{
    /**
     * Constructor.
     */
    public function __construct(RequestContext $context)
    {
        $this->context = $context;
    }

    public function match($pathinfo)
    {
        $allow = array();
        $pathinfo = rawurldecode($pathinfo);
        $context = $this->context;
        $request = $this->request;

        // player
        if (rtrim($pathinfo, '/') === '/player') {
            if (substr($pathinfo, -1) !== '/') {
                return $this->redirect($pathinfo.'/', 'player');
            }

            return array (  '_controller' => 'tuttifruttiweb\\PlayerBundle\\Controller\\PlayerController::indexAction',  '_route' => 'player',);
        }

        // statistics
        if ($pathinfo === '/statistics') {
            return array (  '_controller' => 'tuttifruttiweb\\StatisticsBundle\\Controller\\DefaultController::indexAction',  '_route' => 'statistics',);
        }

        // utils_homepage
        if (0 === strpos($pathinfo, '/hello') && preg_match('#^/hello/(?P<name>[^/]++)$#s', $pathinfo, $matches)) {
            return $this->mergeDefaults(array_replace($matches, array('_route' => 'utils_homepage')), array (  '_controller' => 'tuttifruttiweb\\UtilsBundle\\Controller\\DefaultController::indexAction',));
        }

        if (0 === strpos($pathinfo, '/category')) {
            // category
            if (rtrim($pathinfo, '/') === '/category') {
                if (substr($pathinfo, -1) !== '/') {
                    return $this->redirect($pathinfo.'/', 'category');
                }

                return array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::indexAction',  '_route' => 'category',);
            }

            // category_show
            if (preg_match('#^/category/(?P<id>[^/]++)/show$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'category_show')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::showAction',));
            }

            // category_create
            if ($pathinfo === '/category/create') {
                return array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::createAction',  '_route' => 'category_create',);
            }

            // category_edit
            if (preg_match('#^/category/(?P<id>[^/]++)/edit$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'category_edit')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::editAction',));
            }

            // category_delete
            if (preg_match('#^/category/(?P<id>[^/]++)/delete$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'category_delete')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::deleteAction',));
            }

            // reportedword_accept
            if (preg_match('#^/category/(?P<id>[^/]++)/acceptReportedWord/(?P<word>[^/]++)$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'reportedword_accept')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::addReportedWordAction',));
            }

            // reportedword_reject
            if (preg_match('#^/category/(?P<id>[^/]++)/rejectReportedWord/(?P<word>[^/]++)$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'reportedword_reject')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::removeReportedWordAction',));
            }

            // acceptedword_delete
            if (preg_match('#^/category/(?P<id>[^/]++)/deleteWord/(?P<word>[^/]++)$#s', $pathinfo, $matches)) {
                return $this->mergeDefaults(array_replace($matches, array('_route' => 'acceptedword_delete')), array (  '_controller' => 'tuttifruttiweb\\CategoryBundle\\Controller\\CategoryController::removeAcceptedWordAction',));
            }

        }

        throw 0 < count($allow) ? new MethodNotAllowedException(array_unique($allow)) : new ResourceNotFoundException();
    }
}
