/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    PurchaseHasProductComponentsPage,
    PurchaseHasProductDeleteDialog,
    PurchaseHasProductUpdatePage
} from './purchase-has-product.page-object';

const expect = chai.expect;

describe('PurchaseHasProduct e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let purchaseHasProductUpdatePage: PurchaseHasProductUpdatePage;
    let purchaseHasProductComponentsPage: PurchaseHasProductComponentsPage;
    let purchaseHasProductDeleteDialog: PurchaseHasProductDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PurchaseHasProducts', async () => {
        await navBarPage.goToEntity('purchase-has-product');
        purchaseHasProductComponentsPage = new PurchaseHasProductComponentsPage();
        expect(await purchaseHasProductComponentsPage.getTitle()).to.eq('Purchase Has Products');
    });

    it('should load create PurchaseHasProduct page', async () => {
        await purchaseHasProductComponentsPage.clickOnCreateButton();
        purchaseHasProductUpdatePage = new PurchaseHasProductUpdatePage();
        expect(await purchaseHasProductUpdatePage.getPageTitle()).to.eq('Create or edit a Purchase Has Product');
        await purchaseHasProductUpdatePage.cancel();
    });

    it('should create and save PurchaseHasProducts', async () => {
        const nbButtonsBeforeCreate = await purchaseHasProductComponentsPage.countDeleteButtons();

        await purchaseHasProductComponentsPage.clickOnCreateButton();
        await promise.all([
            purchaseHasProductUpdatePage.setQuantityInput('5'),
            purchaseHasProductUpdatePage.setTaxInput('5'),
            purchaseHasProductUpdatePage.setDatePurchaseInput('2000-12-31'),
            purchaseHasProductUpdatePage.purchaseSelectLastOption()
        ]);
        expect(await purchaseHasProductUpdatePage.getQuantityInput()).to.eq('5');
        expect(await purchaseHasProductUpdatePage.getTaxInput()).to.eq('5');
        expect(await purchaseHasProductUpdatePage.getDatePurchaseInput()).to.eq('2000-12-31');
        await purchaseHasProductUpdatePage.save();
        expect(await purchaseHasProductUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await purchaseHasProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PurchaseHasProduct', async () => {
        const nbButtonsBeforeDelete = await purchaseHasProductComponentsPage.countDeleteButtons();
        await purchaseHasProductComponentsPage.clickOnLastDeleteButton();

        purchaseHasProductDeleteDialog = new PurchaseHasProductDeleteDialog();
        expect(await purchaseHasProductDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Purchase Has Product?');
        await purchaseHasProductDeleteDialog.clickOnConfirmButton();

        expect(await purchaseHasProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
