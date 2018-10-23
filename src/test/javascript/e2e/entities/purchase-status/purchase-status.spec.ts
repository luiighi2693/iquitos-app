/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PurchaseStatusComponentsPage, PurchaseStatusDeleteDialog, PurchaseStatusUpdatePage } from './purchase-status.page-object';

const expect = chai.expect;

describe('PurchaseStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let purchaseStatusUpdatePage: PurchaseStatusUpdatePage;
    let purchaseStatusComponentsPage: PurchaseStatusComponentsPage;
    let purchaseStatusDeleteDialog: PurchaseStatusDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PurchaseStatuses', async () => {
        await navBarPage.goToEntity('purchase-status');
        purchaseStatusComponentsPage = new PurchaseStatusComponentsPage();
        expect(await purchaseStatusComponentsPage.getTitle()).to.eq('Purchase Statuses');
    });

    it('should load create PurchaseStatus page', async () => {
        await purchaseStatusComponentsPage.clickOnCreateButton();
        purchaseStatusUpdatePage = new PurchaseStatusUpdatePage();
        expect(await purchaseStatusUpdatePage.getPageTitle()).to.eq('Create or edit a Purchase Status');
        await purchaseStatusUpdatePage.cancel();
    });

    it('should create and save PurchaseStatuses', async () => {
        const nbButtonsBeforeCreate = await purchaseStatusComponentsPage.countDeleteButtons();

        await purchaseStatusComponentsPage.clickOnCreateButton();
        await promise.all([purchaseStatusUpdatePage.setValueInput('value'), purchaseStatusUpdatePage.setMetaDataInput('metaData')]);
        expect(await purchaseStatusUpdatePage.getValueInput()).to.eq('value');
        expect(await purchaseStatusUpdatePage.getMetaDataInput()).to.eq('metaData');
        await purchaseStatusUpdatePage.save();
        expect(await purchaseStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await purchaseStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PurchaseStatus', async () => {
        const nbButtonsBeforeDelete = await purchaseStatusComponentsPage.countDeleteButtons();
        await purchaseStatusComponentsPage.clickOnLastDeleteButton();

        purchaseStatusDeleteDialog = new PurchaseStatusDeleteDialog();
        expect(await purchaseStatusDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Purchase Status?');
        await purchaseStatusDeleteDialog.clickOnConfirmButton();

        expect(await purchaseStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
