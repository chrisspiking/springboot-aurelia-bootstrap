import {inject} from "aurelia-framework";
import {EventAggregator} from "aurelia-event-aggregator";

@inject(EventAggregator)
export class Home {

    constructor(private eventAggregator: EventAggregator) {

    }

}