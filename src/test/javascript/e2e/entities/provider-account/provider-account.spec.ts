/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProviderAccountComponentsPage, ProviderAccountDeleteDialog, ProviderAccountUpdatePage } from './provider-account.page-object';

const expect = chai.expect;

describe('ProviderAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let providerAccountUpdatePage: ProviderAccountUpdatePage;
    let providerAccountComponentsPage: ProviderAccountComponentsPage;
    let providerAccountDeleteDialog: ProviderAccountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProviderAccounts', async () => {
        await navBarPage.goToEntity('provider-account');
        providerAccountComponentsPage = new ProviderAccountComponentsPage();
        expect(await providerAccountComponentsPage.getTitle()).to.eq('Provider Accounts');
    });

    it('should load create ProviderAccount page', async () => {
        await providerAccountComponentsPage.clickOnCreateButton();
        providerAccountUpdatePage = new ProviderAccountUpdatePage();
        expect(await providerAccountUpdatePage.getPageTitle()).to.eq('Create or edit a Provider Account');
        await providerAccountUpdatePage.cancel();
    });

    it('should create and save ProviderAccounts', async () => {
        const nbButtonsBeforeCreate = await providerAccountComponentsPage.countDeleteButtons();

        await providerAccountComponentsPage.clickOnCreateButton();
        await promise.all([
            providerAccountUpdatePage.setCodeInput('code'),
            providerAccountUpdatePage.statusSelectLastOption(),
            providerAccountUpdatePage.setBankInput('bank'),
            providerAccountUpdatePage.setAccountNameInput('accountName'),
            providerAccountUpdatePage.setAccountNumberInput('5'),
            providerAccountUpdatePage.setInitDateInput('2000-12-31'),
            providerAccountUpdatePage.providerSelectLastOption()
        ]);
        expect(await providerAccountUpdatePage.getCodeInput()).to.eq('code');
        expect(await providerAccountUpdatePage.getBankInput()).to.eq('bank');
        expect(await providerAccountUpdatePage.getAccountNameInput()).to.eq('accountName');
        expect(await providerAccountUpdatePage.getAccountNumberInput()).to.eq('5');
        expect(await providerAccountUpdatePage.getInitDateInput()).to.eq('2000-12-31');
        await providerAccountUpdatePage.save();
        expect(await providerAccountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await providerAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProviderAccount', async () => {
        const nbButtonsBeforeDelete = await providerAccountComponentsPage.countDeleteButtons();
        await providerAccountComponentsPage.clickOnLastDeleteButton();

        providerAccountDeleteDialog = new ProviderAccountDeleteDialog();
        expect(await providerAccountDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Provider Account?');
        await providerAccountDeleteDialog.clickOnConfirmButton();

        expect(await providerAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
