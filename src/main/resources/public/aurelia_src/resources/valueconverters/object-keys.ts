export class ObjectKeysValueConverter {

    toView(jsonObj){
        if(jsonObj) {
            return Object.keys(jsonObj);
        } else {
            return [];
        }
    }

}