/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SellHasProductComponentsPage, SellHasProductDeleteDialog, SellHasProductUpdatePage } from './sell-has-product.page-object';

const expect = chai.expect;

describe('SellHasProduct e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let sellHasProductUpdatePage: SellHasProductUpdatePage;
    let sellHasProductComponentsPage: SellHasProductComponentsPage;
    let sellHasProductDeleteDialog: SellHasProductDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SellHasProducts', async () => {
        await navBarPage.goToEntity('sell-has-product');
        sellHasProductComponentsPage = new SellHasProductComponentsPage();
        expect(await sellHasProductComponentsPage.getTitle()).to.eq('Sell Has Products');
    });

    it('should load create SellHasProduct page', async () => {
        await sellHasProductComponentsPage.clickOnCreateButton();
        sellHasProductUpdatePage = new SellHasProductUpdatePage();
        expect(await sellHasProductUpdatePage.getPageTitle()).to.eq('Create or edit a Sell Has Product');
        await sellHasProductUpdatePage.cancel();
    });

    it('should create and save SellHasProducts', async () => {
        const nbButtonsBeforeCreate = await sellHasProductComponentsPage.countDeleteButtons();

        await sellHasProductComponentsPage.clickOnCreateButton();
        await promise.all([
            sellHasProductUpdatePage.setQuantityInput('5'),
            sellHasProductUpdatePage.setDiscountInput('5'),
            sellHasProductUpdatePage.sellSelectLastOption(),
            sellHasProductUpdatePage.variantSelectLastOption()
        ]);
        expect(await sellHasProductUpdatePage.getQuantityInput()).to.eq('5');
        expect(await sellHasProductUpdatePage.getDiscountInput()).to.eq('5');
        await sellHasProductUpdatePage.save();
        expect(await sellHasProductUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await sellHasProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SellHasProduct', async () => {
        const nbButtonsBeforeDelete = await sellHasProductComponentsPage.countDeleteButtons();
        await sellHasProductComponentsPage.clickOnLastDeleteButton();

        sellHasProductDeleteDialog = new SellHasProductDeleteDialog();
        expect(await sellHasProductDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Sell Has Product?');
        await sellHasProductDeleteDialog.clickOnConfirmButton();

        expect(await sellHasProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
