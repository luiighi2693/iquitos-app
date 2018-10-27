/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderProductComponentsPage, OrderProductDeleteDialog, OrderProductUpdatePage } from './order-product.page-object';

const expect = chai.expect;

describe('OrderProduct e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let orderProductUpdatePage: OrderProductUpdatePage;
    let orderProductComponentsPage: OrderProductComponentsPage;
    let orderProductDeleteDialog: OrderProductDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load OrderProducts', async () => {
        await navBarPage.goToEntity('order-product');
        orderProductComponentsPage = new OrderProductComponentsPage();
        expect(await orderProductComponentsPage.getTitle()).to.eq('Order Products');
    });

    it('should load create OrderProduct page', async () => {
        await orderProductComponentsPage.clickOnCreateButton();
        orderProductUpdatePage = new OrderProductUpdatePage();
        expect(await orderProductUpdatePage.getPageTitle()).to.eq('Create or edit a Order Product');
        await orderProductUpdatePage.cancel();
    });

    it('should create and save OrderProducts', async () => {
        const nbButtonsBeforeCreate = await orderProductComponentsPage.countDeleteButtons();

        await orderProductComponentsPage.clickOnCreateButton();
        await promise.all([
            orderProductUpdatePage.setNoteInput('note'),
            orderProductUpdatePage.setGuideInput('guide'),
            orderProductUpdatePage.orderStatusSelectLastOption(),
            orderProductUpdatePage.setMetaDataInput('metaData'),
            orderProductUpdatePage.providerSelectLastOption()
            // orderProductUpdatePage.productsSelectLastOption(),
        ]);
        expect(await orderProductUpdatePage.getNoteInput()).to.eq('note');
        expect(await orderProductUpdatePage.getGuideInput()).to.eq('guide');
        expect(await orderProductUpdatePage.getMetaDataInput()).to.eq('metaData');
        await orderProductUpdatePage.save();
        expect(await orderProductUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await orderProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last OrderProduct', async () => {
        const nbButtonsBeforeDelete = await orderProductComponentsPage.countDeleteButtons();
        await orderProductComponentsPage.clickOnLastDeleteButton();

        orderProductDeleteDialog = new OrderProductDeleteDialog();
        expect(await orderProductDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Order Product?');
        await orderProductDeleteDialog.clickOnConfirmButton();

        expect(await orderProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
