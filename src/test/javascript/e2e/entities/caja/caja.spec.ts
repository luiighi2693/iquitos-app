/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CajaComponentsPage, CajaDeleteDialog, CajaUpdatePage } from './caja.page-object';

const expect = chai.expect;

describe('Caja e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let cajaUpdatePage: CajaUpdatePage;
    let cajaComponentsPage: CajaComponentsPage;
    let cajaDeleteDialog: CajaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cajas', async () => {
        await navBarPage.goToEntity('caja');
        cajaComponentsPage = new CajaComponentsPage();
        expect(await cajaComponentsPage.getTitle()).to.eq('Cajas');
    });

    it('should load create Caja page', async () => {
        await cajaComponentsPage.clickOnCreateButton();
        cajaUpdatePage = new CajaUpdatePage();
        expect(await cajaUpdatePage.getPageTitle()).to.eq('Create or edit a Caja');
        await cajaUpdatePage.cancel();
    });

    it('should create and save Cajas', async () => {
        const nbButtonsBeforeCreate = await cajaComponentsPage.countDeleteButtons();

        await cajaComponentsPage.clickOnCreateButton();
        await promise.all([
            cajaUpdatePage.setMontoInput('5'),
            cajaUpdatePage.setMontoActualInput('5'),
            cajaUpdatePage.setFechaInicialInput('2000-12-31'),
            cajaUpdatePage.setFechaFinalInput('2000-12-31'),
            cajaUpdatePage.cajaSelectLastOption()
        ]);
        expect(await cajaUpdatePage.getMontoInput()).to.eq('5');
        expect(await cajaUpdatePage.getMontoActualInput()).to.eq('5');
        expect(await cajaUpdatePage.getFechaInicialInput()).to.eq('2000-12-31');
        expect(await cajaUpdatePage.getFechaFinalInput()).to.eq('2000-12-31');
        await cajaUpdatePage.save();
        expect(await cajaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await cajaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Caja', async () => {
        const nbButtonsBeforeDelete = await cajaComponentsPage.countDeleteButtons();
        await cajaComponentsPage.clickOnLastDeleteButton();

        cajaDeleteDialog = new CajaDeleteDialog();
        expect(await cajaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Caja?');
        await cajaDeleteDialog.clickOnConfirmButton();

        expect(await cajaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
