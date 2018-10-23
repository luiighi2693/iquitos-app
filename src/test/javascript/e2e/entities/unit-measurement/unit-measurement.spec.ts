/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UnitMeasurementComponentsPage, UnitMeasurementDeleteDialog, UnitMeasurementUpdatePage } from './unit-measurement.page-object';

const expect = chai.expect;

describe('UnitMeasurement e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let unitMeasurementUpdatePage: UnitMeasurementUpdatePage;
    let unitMeasurementComponentsPage: UnitMeasurementComponentsPage;
    let unitMeasurementDeleteDialog: UnitMeasurementDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UnitMeasurements', async () => {
        await navBarPage.goToEntity('unit-measurement');
        unitMeasurementComponentsPage = new UnitMeasurementComponentsPage();
        expect(await unitMeasurementComponentsPage.getTitle()).to.eq('Unit Measurements');
    });

    it('should load create UnitMeasurement page', async () => {
        await unitMeasurementComponentsPage.clickOnCreateButton();
        unitMeasurementUpdatePage = new UnitMeasurementUpdatePage();
        expect(await unitMeasurementUpdatePage.getPageTitle()).to.eq('Create or edit a Unit Measurement');
        await unitMeasurementUpdatePage.cancel();
    });

    it('should create and save UnitMeasurements', async () => {
        const nbButtonsBeforeCreate = await unitMeasurementComponentsPage.countDeleteButtons();

        await unitMeasurementComponentsPage.clickOnCreateButton();
        await promise.all([unitMeasurementUpdatePage.setValueInput('value'), unitMeasurementUpdatePage.setMetaDataInput('metaData')]);
        expect(await unitMeasurementUpdatePage.getValueInput()).to.eq('value');
        expect(await unitMeasurementUpdatePage.getMetaDataInput()).to.eq('metaData');
        await unitMeasurementUpdatePage.save();
        expect(await unitMeasurementUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await unitMeasurementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UnitMeasurement', async () => {
        const nbButtonsBeforeDelete = await unitMeasurementComponentsPage.countDeleteButtons();
        await unitMeasurementComponentsPage.clickOnLastDeleteButton();

        unitMeasurementDeleteDialog = new UnitMeasurementDeleteDialog();
        expect(await unitMeasurementDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Unit Measurement?');
        await unitMeasurementDeleteDialog.clickOnConfirmButton();

        expect(await unitMeasurementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
