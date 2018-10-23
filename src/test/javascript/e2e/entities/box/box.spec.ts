/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoxComponentsPage, BoxDeleteDialog, BoxUpdatePage } from './box.page-object';

const expect = chai.expect;

describe('Box e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let boxUpdatePage: BoxUpdatePage;
    let boxComponentsPage: BoxComponentsPage;
    let boxDeleteDialog: BoxDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Boxes', async () => {
        await navBarPage.goToEntity('box');
        boxComponentsPage = new BoxComponentsPage();
        expect(await boxComponentsPage.getTitle()).to.eq('Boxes');
    });

    it('should load create Box page', async () => {
        await boxComponentsPage.clickOnCreateButton();
        boxUpdatePage = new BoxUpdatePage();
        expect(await boxUpdatePage.getPageTitle()).to.eq('Create or edit a Box');
        await boxUpdatePage.cancel();
    });

    it('should create and save Boxes', async () => {
        const nbButtonsBeforeCreate = await boxComponentsPage.countDeleteButtons();

        await boxComponentsPage.clickOnCreateButton();
        await promise.all([
            boxUpdatePage.setInitAmountInput('5'),
            boxUpdatePage.setActualAmountInput('5'),
            boxUpdatePage.setInitDateInput('2000-12-31'),
            boxUpdatePage.setEndDateInput('2000-12-31'),
            boxUpdatePage.boxSelectLastOption()
        ]);
        expect(await boxUpdatePage.getInitAmountInput()).to.eq('5');
        expect(await boxUpdatePage.getActualAmountInput()).to.eq('5');
        expect(await boxUpdatePage.getInitDateInput()).to.eq('2000-12-31');
        expect(await boxUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await boxUpdatePage.save();
        expect(await boxUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await boxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Box', async () => {
        const nbButtonsBeforeDelete = await boxComponentsPage.countDeleteButtons();
        await boxComponentsPage.clickOnLastDeleteButton();

        boxDeleteDialog = new BoxDeleteDialog();
        expect(await boxDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Box?');
        await boxDeleteDialog.clickOnConfirmButton();

        expect(await boxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
