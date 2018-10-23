/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    DocumentTypePurchaseComponentsPage,
    DocumentTypePurchaseDeleteDialog,
    DocumentTypePurchaseUpdatePage
} from './document-type-purchase.page-object';

const expect = chai.expect;

describe('DocumentTypePurchase e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentTypePurchaseUpdatePage: DocumentTypePurchaseUpdatePage;
    let documentTypePurchaseComponentsPage: DocumentTypePurchaseComponentsPage;
    let documentTypePurchaseDeleteDialog: DocumentTypePurchaseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentTypePurchases', async () => {
        await navBarPage.goToEntity('document-type-purchase');
        documentTypePurchaseComponentsPage = new DocumentTypePurchaseComponentsPage();
        expect(await documentTypePurchaseComponentsPage.getTitle()).to.eq('Document Type Purchases');
    });

    it('should load create DocumentTypePurchase page', async () => {
        await documentTypePurchaseComponentsPage.clickOnCreateButton();
        documentTypePurchaseUpdatePage = new DocumentTypePurchaseUpdatePage();
        expect(await documentTypePurchaseUpdatePage.getPageTitle()).to.eq('Create or edit a Document Type Purchase');
        await documentTypePurchaseUpdatePage.cancel();
    });

    it('should create and save DocumentTypePurchases', async () => {
        const nbButtonsBeforeCreate = await documentTypePurchaseComponentsPage.countDeleteButtons();

        await documentTypePurchaseComponentsPage.clickOnCreateButton();
        await promise.all([
            documentTypePurchaseUpdatePage.setValueInput('value'),
            documentTypePurchaseUpdatePage.setMetaDataInput('metaData')
        ]);
        expect(await documentTypePurchaseUpdatePage.getValueInput()).to.eq('value');
        expect(await documentTypePurchaseUpdatePage.getMetaDataInput()).to.eq('metaData');
        await documentTypePurchaseUpdatePage.save();
        expect(await documentTypePurchaseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentTypePurchaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentTypePurchase', async () => {
        const nbButtonsBeforeDelete = await documentTypePurchaseComponentsPage.countDeleteButtons();
        await documentTypePurchaseComponentsPage.clickOnLastDeleteButton();

        documentTypePurchaseDeleteDialog = new DocumentTypePurchaseDeleteDialog();
        expect(await documentTypePurchaseDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Document Type Purchase?'
        );
        await documentTypePurchaseDeleteDialog.clickOnConfirmButton();

        expect(await documentTypePurchaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
