/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProviderComponentsPage, ProviderDeleteDialog, ProviderUpdatePage } from './provider.page-object';

const expect = chai.expect;

describe('Provider e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let providerUpdatePage: ProviderUpdatePage;
    let providerComponentsPage: ProviderComponentsPage;
    let providerDeleteDialog: ProviderDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Providers', async () => {
        await navBarPage.goToEntity('provider');
        providerComponentsPage = new ProviderComponentsPage();
        expect(await providerComponentsPage.getTitle()).to.eq('Providers');
    });

    it('should load create Provider page', async () => {
        await providerComponentsPage.clickOnCreateButton();
        providerUpdatePage = new ProviderUpdatePage();
        expect(await providerUpdatePage.getPageTitle()).to.eq('Create or edit a Provider');
        await providerUpdatePage.cancel();
    });

    it('should create and save Providers', async () => {
        const nbButtonsBeforeCreate = await providerComponentsPage.countDeleteButtons();

        await providerComponentsPage.clickOnCreateButton();
        await promise.all([
            providerUpdatePage.setCodeInput('code'),
            providerUpdatePage.setSocialReasonInput('socialReason'),
            providerUpdatePage.setAddressInput('address'),
            providerUpdatePage.setEmailInput('email'),
            providerUpdatePage.setCellphoneInput('5')
        ]);
        expect(await providerUpdatePage.getCodeInput()).to.eq('code');
        expect(await providerUpdatePage.getSocialReasonInput()).to.eq('socialReason');
        expect(await providerUpdatePage.getAddressInput()).to.eq('address');
        expect(await providerUpdatePage.getEmailInput()).to.eq('email');
        expect(await providerUpdatePage.getCellphoneInput()).to.eq('5');
        await providerUpdatePage.save();
        expect(await providerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await providerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Provider', async () => {
        const nbButtonsBeforeDelete = await providerComponentsPage.countDeleteButtons();
        await providerComponentsPage.clickOnLastDeleteButton();

        providerDeleteDialog = new ProviderDeleteDialog();
        expect(await providerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Provider?');
        await providerDeleteDialog.clickOnConfirmButton();

        expect(await providerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
