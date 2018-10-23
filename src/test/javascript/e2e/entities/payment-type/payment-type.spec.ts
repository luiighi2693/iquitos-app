/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentTypeComponentsPage, PaymentTypeDeleteDialog, PaymentTypeUpdatePage } from './payment-type.page-object';

const expect = chai.expect;

describe('PaymentType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let paymentTypeUpdatePage: PaymentTypeUpdatePage;
    let paymentTypeComponentsPage: PaymentTypeComponentsPage;
    let paymentTypeDeleteDialog: PaymentTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PaymentTypes', async () => {
        await navBarPage.goToEntity('payment-type');
        paymentTypeComponentsPage = new PaymentTypeComponentsPage();
        expect(await paymentTypeComponentsPage.getTitle()).to.eq('Payment Types');
    });

    it('should load create PaymentType page', async () => {
        await paymentTypeComponentsPage.clickOnCreateButton();
        paymentTypeUpdatePage = new PaymentTypeUpdatePage();
        expect(await paymentTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Payment Type');
        await paymentTypeUpdatePage.cancel();
    });

    it('should create and save PaymentTypes', async () => {
        const nbButtonsBeforeCreate = await paymentTypeComponentsPage.countDeleteButtons();

        await paymentTypeComponentsPage.clickOnCreateButton();
        await promise.all([paymentTypeUpdatePage.setValueInput('value'), paymentTypeUpdatePage.setMetaDataInput('metaData')]);
        expect(await paymentTypeUpdatePage.getValueInput()).to.eq('value');
        expect(await paymentTypeUpdatePage.getMetaDataInput()).to.eq('metaData');
        await paymentTypeUpdatePage.save();
        expect(await paymentTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await paymentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PaymentType', async () => {
        const nbButtonsBeforeDelete = await paymentTypeComponentsPage.countDeleteButtons();
        await paymentTypeComponentsPage.clickOnLastDeleteButton();

        paymentTypeDeleteDialog = new PaymentTypeDeleteDialog();
        expect(await paymentTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payment Type?');
        await paymentTypeDeleteDialog.clickOnConfirmButton();

        expect(await paymentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
