import {Aurelia} from 'aurelia-framework';

export function configure(aurelia: Aurelia) {
    aurelia.use
        .standardConfiguration()
        .developmentLogging()
        // Here are all the defaults that are set on aurelia-bootstrap for easy reference / changeability.
        // From here: https://tochoromero.github.io/aurelia-bootstrap/#/defaults
        .plugin('aurelia-bootstrap', config => {
            config.options.accordionCloseOthers = true,
            config.options.accordionGroupPanelClass = 'panel-default',
            config.options.btnLoadingText= 'Loading...',
            config.options.dropdownAutoClose= 'always',
            config.options.paginationBoundaryLinks= false,
            config.options.paginationDirectionLinks= true,
            config.options.paginationFirstText= 'First',
            config.options.paginationHideSinglePage= true,
            config.options.paginationLastText= 'Last',
            config.options.paginationNextText= '>',
            config.options.paginationPreviousText= '<',
            config.options.popoverPosition= 'top',
            config.options.popoverTrigger= 'mouseover',
            config.options.tabsetType= 'tabs',
            config.options.tabsetVertical= false,
            config.options.tooltipPosition= 'top',
            config.options.tooltipTrigger= 'mouseover'})
        .globalResources([
            'aurelia_src/resources/on-enter',
            'aurelia_src/resources/valueconverters/date-format',
            'aurelia_src/resources/valueconverters/json-string',
            'aurelia_src/resources/valueconverters/to-uppercase',
            'aurelia_src/resources/valueconverters/syntax-highlight',
            'aurelia_src/resources/valueconverters/object-keys'
        ]);

    aurelia.start().then(() => aurelia.setRoot('aurelia_src/app'));
}