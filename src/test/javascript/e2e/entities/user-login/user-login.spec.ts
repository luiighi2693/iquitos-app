/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserLoginComponentsPage, UserLoginDeleteDialog, UserLoginUpdatePage } from './user-login.page-object';

const expect = chai.expect;

describe('UserLogin e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userLoginUpdatePage: UserLoginUpdatePage;
    let userLoginComponentsPage: UserLoginComponentsPage;
    let userLoginDeleteDialog: UserLoginDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserLogins', async () => {
        await navBarPage.goToEntity('user-login');
        userLoginComponentsPage = new UserLoginComponentsPage();
        expect(await userLoginComponentsPage.getTitle()).to.eq('User Logins');
    });

    it('should load create UserLogin page', async () => {
        await userLoginComponentsPage.clickOnCreateButton();
        userLoginUpdatePage = new UserLoginUpdatePage();
        expect(await userLoginUpdatePage.getPageTitle()).to.eq('Create or edit a User Login');
        await userLoginUpdatePage.cancel();
    });

    it('should create and save UserLogins', async () => {
        const nbButtonsBeforeCreate = await userLoginComponentsPage.countDeleteButtons();

        await userLoginComponentsPage.clickOnCreateButton();
        await promise.all([
            userLoginUpdatePage.setDniInput('5'),
            userLoginUpdatePage.setPinInput('5'),
            userLoginUpdatePage.setEmailInput('email')
        ]);
        expect(await userLoginUpdatePage.getDniInput()).to.eq('5');
        expect(await userLoginUpdatePage.getPinInput()).to.eq('5');
        expect(await userLoginUpdatePage.getEmailInput()).to.eq('email');
        await userLoginUpdatePage.save();
        expect(await userLoginUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userLoginComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserLogin', async () => {
        const nbButtonsBeforeDelete = await userLoginComponentsPage.countDeleteButtons();
        await userLoginComponentsPage.clickOnLastDeleteButton();

        userLoginDeleteDialog = new UserLoginDeleteDialog();
        expect(await userLoginDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Login?');
        await userLoginDeleteDialog.clickOnConfirmButton();

        expect(await userLoginComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
