import { copyFile, constants } from 'node:fs';

copyFile('./DigTer_Model/DigitalTerritory_Model.vpp', './DigTer_Model/DigitalTerritory_Model.local.vpp', constants.COPYFILE_FICLONE ,err => {
    if(err) throw err;
    console.log('global file copied to local file');
})