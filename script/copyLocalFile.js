import { copyFile, constants } from 'node:fs';

copyFile('./DigTer_Model/DigitalTerritory_Model.local.vpp', './DigTer_Model/DigitalTerritory_Model.vpp', constants.COPYFILE_FICLONE ,err => {
    if(err) throw err;
    console.log('local file copied to global file');
})