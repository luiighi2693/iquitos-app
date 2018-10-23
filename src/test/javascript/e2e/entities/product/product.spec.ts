/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Product e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productUpdatePage: ProductUpdatePage;
    let productComponentsPage: ProductComponentsPage;
    let productDeleteDialog: ProductDeleteDialog;
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

    it('should load Products', async () => {
        await navBarPage.goToEntity('product');
        productComponentsPage = new ProductComponentsPage();
        expect(await productComponentsPage.getTitle()).to.eq('Products');
    });

    it('should load create Product page', async () => {
        await productComponentsPage.clickOnCreateButton();
        productUpdatePage = new ProductUpdatePage();
        expect(await productUpdatePage.getPageTitle()).to.eq('Create or edit a Product');
        await productUpdatePage.cancel();
    });

    it('should create and save Products', async () => {
        const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

        await productComponentsPage.clickOnCreateButton();
        await promise.all([
            productUpdatePage.setCodeInput('code'),
            productUpdatePage.setDescriptionInput('description'),
            productUpdatePage.setDniInput('5'),
            productUpdatePage.setExpirationDateInput('2000-12-31'),
            productUpdatePage.setImageInput(absolutePath),
            productUpdatePage.setStockInput('5'),
            productUpdatePage.setStockLimitNotificationInput('5'),
            productUpdatePage.productTypeSelectLastOption(),
            productUpdatePage.unitMeasurementSelectLastOption(),
            productUpdatePage.categorySelectLastOption(),
            productUpdatePage.vartiantSelectLastOption(),
            productUpdatePage.variantSelectLastOption(),
            productUpdatePage.sellHasProductSelectLastOption(),
            productUpdatePage.purchaseHasProductSelectLastOption()
        ]);
        expect(await productUpdatePage.getCodeInput()).to.eq('code');
        expect(await productUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await productUpdatePage.getDniInput()).to.eq('5');
        expect(await productUpdatePage.getExpirationDateInput()).to.eq('2000-12-31');
        const selectedIsFavorite = productUpdatePage.getIsFavoriteInput();
        if (await selectedIsFavorite.isSelected()) {
            await productUpdatePage.getIsFavoriteInput().click();
            expect(await productUpdatePage.getIsFavoriteInput().isSelected()).to.be.false;
        } else {
            await productUpdatePage.getIsFavoriteInput().click();
            expect(await productUpdatePage.getIsFavoriteInput().isSelected()).to.be.true;
        }
        const selectedVisibleToSell = productUpdatePage.getVisibleToSellInput();
        if (await selectedVisibleToSell.isSelected()) {
            await productUpdatePage.getVisibleToSellInput().click();
            expect(await productUpdatePage.getVisibleToSellInput().isSelected()).to.be.false;
        } else {
            await productUpdatePage.getVisibleToSellInput().click();
            expect(await productUpdatePage.getVisibleToSellInput().isSelected()).to.be.true;
        }
        expect(await productUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        expect(await productUpdatePage.getStockInput()).to.eq('5');
        expect(await productUpdatePage.getStockLimitNotificationInput()).to.eq('5');
        await productUpdatePage.save();
        expect(await productUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Product', async () => {
        const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
        await productComponentsPage.clickOnLastDeleteButton();

        productDeleteDialog = new ProductDeleteDialog();
        expect(await productDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Product?');
        await productDeleteDialog.clickOnConfirmButton();

        expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
