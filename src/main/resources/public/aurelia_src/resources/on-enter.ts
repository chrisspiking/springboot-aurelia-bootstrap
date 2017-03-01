import {inject} from "aurelia-framework";

@inject(Element)
export class OnEnterCustomAttribute {

    constructor(element:Element) {
        this.element = element;

        this.onKeyPress = (ev:KeyboardEvent) => {
            //Enter keyCode is 13
            if (ev.keyCode !== 13) return;
            this.action();
        };
    }

    element:Element;
    attached() {
        this.element.addEventListener("keyup", this.onKeyPress);
    }
    onKeyPress;

    action;
    valueChanged(func) {
        this.action = func;
    }

    detached() {
        this.element.removeEventListener("keyup", this.onKeyPress);
    }
}