export class JsonStringValueConverter {

    toView(value) {
        return JSON.stringify(value);
    }

}