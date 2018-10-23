/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProviderPaymentComponentsPage, ProviderPaymentDeleteDialog, ProviderPaymentUpdatePage } from './provider-payment.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ProviderPayment e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let providerPaymentUpdatePage: ProviderPaymentUpdatePage;
    let providerPaymentComponentsPage: ProviderPaymentComponentsPage;
    let providerPaymentDeleteDialog: ProviderPaymentDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProviderPayments', async () => {
        await navBarPage.goToEntity('provider-payment');
        providerPaymentComponentsPage = new ProviderPaymentComponentsPage();
        expect(await providerPaymentComponentsPage.getTitle()).to.eq('Provider Payments');
    });

    it('should load create ProviderPayment page', async () => {
        await providerPaymentComponentsPage.clickOnCreateButton();
        providerPaymentUpdatePage = new ProviderPaymentUpdatePage();
        expect(await providerPaymentUpdatePage.getPageTitle()).to.eq('Create or edit a Provider Payment');
        await providerPaymentUpdatePage.cancel();
    });

    it('should create and save ProviderPayments', async () => {
        const nbButtonsBeforeCreate = await providerPaymentComponentsPage.countDeleteButtons();

        await providerPaymentComponentsPage.clickOnCreateButton();
        await promise.all([
            providerPaymentUpdatePage.setAmountToPayInput('5'),
            providerPaymentUpdatePage.setAmountPayedInput('5'),
            providerPaymentUpdatePage.setEmissionDateInput('2000-12-31'),
            providerPaymentUpdatePage.setDocumentCodeInput('documentCode'),
            providerPaymentUpdatePage.setGlosaInput('glosa'),
            providerPaymentUpdatePage.setImageInput(absolutePath),
            providerPaymentUpdatePage.documentTypePurchaseSelectLastOption(),
            providerPaymentUpdatePage.paymentTypeSelectLastOption()
        ]);
        expect(await providerPaymentUpdatePage.getAmountToPayInput()).to.eq('5');
        expect(await providerPaymentUpdatePage.getAmountPayedInput()).to.eq('5');
        expect(await providerPaymentUpdatePage.getEmissionDateInput()).to.eq('2000-12-31');
        expect(await providerPaymentUpdatePage.getDocumentCodeInput()).to.eq('documentCode');
        expect(await providerPaymentUpdatePage.getGlosaInput()).to.eq('glosa');
        expect(await providerPaymentUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        await providerPaymentUpdatePage.save();
        expect(await providerPaymentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await providerPaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProviderPayment', async () => {
        const nbButtonsBeforeDelete = await providerPaymentComponentsPage.countDeleteButtons();
        await providerPaymentComponentsPage.clickOnLastDeleteButton();

        providerPaymentDeleteDialog = new ProviderPaymentDeleteDialog();
        expect(await providerPaymentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Provider Payment?');
        await providerPaymentDeleteDialog.clickOnConfirmButton();

        expect(await providerPaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
