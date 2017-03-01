import {Router, RouterConfiguration} from 'aurelia-router';

export class App {

    router: Router;

    constructor() {

    }

    public configureRouter(config: RouterConfiguration, router: Router): void {
        this.router = router;
        config.title = 'FrontendTests';

        config.map([
            {route: 'home',  name: 'home',  moduleId: './features/home/home', title: 'Home', nav: true},
            {route: '/', redirect: 'home'}
        ]);

        config.mapUnknownRoutes(() => {
            return '/';
        });
    }

}