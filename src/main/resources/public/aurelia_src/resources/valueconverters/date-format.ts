import * as moment from 'moment';
import 'moment-timezone';

export class DateFormatValueConverter {
    toView(value, timeZone) {
        if(timeZone) {
            return moment.utc(value).tz(timeZone).format('DD/MM/YYYY HH:mm');
        } else {
            return moment.utc(value).tz("Europe/London").format('DD/MM/YYYY HH:mm');
        }
    }
}